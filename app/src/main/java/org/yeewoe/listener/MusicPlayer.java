package org.yeewoe.listener;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.Arrays;
import java.util.WeakHashMap;

import org.yeewoe.listener.dataloader.SongLoader;
import org.yeewoe.listener.mvp.model.MusicPlaybackTrack;
import org.yeewoe.listener.util.ListenerUtil;

/**
 * Created by yeewoe on 2016/10/8.
 */

public class MusicPlayer {

    private static final WeakHashMap<Context, ServiceBinder> mConnectionMap;
    private static final long[] sEmptyList;
    public static IListenerService mService = null;
    private static ContentValues[] mContentValuesCache = null;

    static {
        mConnectionMap = new WeakHashMap<Context, ServiceBinder>();
        sEmptyList = new long[0];
    }

    public static ServiceToken bindToService(final Context context,
                                             final ServiceConnection callback) {

        Activity realActivity = ((Activity) context).getParent();
        if (realActivity == null) {
            realActivity = (Activity) context;
        }
        final ContextWrapper contextWrapper = new ContextWrapper(realActivity);
        contextWrapper.startService(new Intent(contextWrapper, MusicService.class));
        final ServiceBinder binder = new ServiceBinder(callback,
                contextWrapper.getApplicationContext());
        if (contextWrapper.bindService(
                new Intent().setClass(contextWrapper, MusicService.class), binder, 0)) {
            mConnectionMap.put(contextWrapper, binder);
            return new ServiceToken(contextWrapper);
        }
        return null;
    }

    public static void unbindFromService(final ServiceToken token) {
        if (token == null) {
            return;
        }
        final ContextWrapper mContextWrapper = token.mWrappedContext;
        final ServiceBinder mBinder = mConnectionMap.remove(mContextWrapper);
        if (mBinder == null) {
            return;
        }
        mContextWrapper.unbindService(mBinder);
        if (mConnectionMap.isEmpty()) {
            mService = null;
        }
    }

    public static boolean isPlaybackServiceConnected() {
        return mService != null;
    }

    public static void next() {
        try {
            if (mService != null) {
                mService.next();
            }
        } catch (final RemoteException ignored) {
        }
    }

    public static void initPlaybackServiceWithSettings(final Context context) {
        setShowAlbumArtOnLockscreen(true);
    }

    /**
     * 在锁屏显示专辑封面
     * @param enabled
     */
    public static void setShowAlbumArtOnLockscreen(final boolean enabled) {
        try {
            if (mService != null) {
                mService.setLockscreenAlbumArt(enabled);
            }
        } catch (final RemoteException ignored) {
        }
    }

    public static void asyncNext(final Context context) {
        final Intent previous = new Intent(context, MusicService.class);
        previous.setAction(MusicService.NEXT_ACTION);
        context.startService(previous);
    }

    public static void previous(final Context context, final boolean force) {
        final Intent previous = new Intent(context, MusicService.class);
        if (force) {
            previous.setAction(MusicService.PREVIOUS_FORCE_ACTION);
        } else {
            previous.setAction(MusicService.PREVIOUS_ACTION);
        }
        context.startService(previous);
    }

    public static void playOrPause() {
        try {
            if (mService != null) {
                if (mService.isPlaying()) {
                    mService.pause();
                } else {
                    mService.play();
                }
            }
        } catch (final Exception ignored) {
        }
    }

    public static void cycleRepeat() {
        try {
            if (mService != null) {
                switch (mService.getRepeatMode()) {
                    case MusicService.REPEAT_NONE:
                        mService.setRepeatMode(MusicService.REPEAT_ALL);
                        break;
                    case MusicService.REPEAT_ALL:
                        mService.setRepeatMode(MusicService.REPEAT_CURRENT);
                        if (mService.getShuffleMode() != MusicService.SHUFFLE_NONE) {
                            mService.setShuffleMode(MusicService.SHUFFLE_NONE);
                        }
                        break;
                    default:
                        mService.setRepeatMode(MusicService.REPEAT_NONE);
                        break;
                }
            }
        } catch (final RemoteException ignored) {
        }
    }

    public static void cycleShuffle() {
        try {
            if (mService != null) {
                switch (mService.getShuffleMode()) {
                    case MusicService.SHUFFLE_NONE:
                        mService.setShuffleMode(MusicService.SHUFFLE_NORMAL);
                        if (mService.getRepeatMode() == MusicService.REPEAT_CURRENT) {
                            mService.setRepeatMode(MusicService.REPEAT_ALL);
                        }
                        break;
                    case MusicService.SHUFFLE_NORMAL:
                        mService.setShuffleMode(MusicService.SHUFFLE_NONE);
                        break;
                    case MusicService.SHUFFLE_AUTO:
                        mService.setShuffleMode(MusicService.SHUFFLE_NONE);
                        break;
                    default:
                        break;
                }
            }
        } catch (final RemoteException ignored) {
        }
    }

    public static boolean isPlaying() {
        if (mService != null) {
            try {
                return mService.isPlaying();
            } catch (final RemoteException ignored) {
            }
        }
        return false;
    }

    public static int getShuffleMode() {
        if (mService != null) {
            try {
                return mService.getShuffleMode();
            } catch (final RemoteException ignored) {
            }
        }
        return 0;
    }

    public static void setShuffleMode(int mode) {
        try {
            if (mService != null) {
                mService.setShuffleMode(mode);
            }
        } catch (RemoteException ignored) {

        }
    }

    public static void setRepeatMode(int mode) {
        try {
            if (mService != null) {
                mService.setRepeatMode(mode);
            }
        } catch (RemoteException ignored) {

        }
    }

    public static int getRepeatMode() {
        if (mService != null) {
            try {
                return mService.getRepeatMode();
            } catch (final RemoteException ignored) {
            }
        }
        return 0;
    }

    public static String getTrackName() {
        if (mService != null) {
            try {
                return mService.getTrackName();
            } catch (final RemoteException ignored) {
            }
        }
        return null;
    }

    public static String getArtistName() {
        if (mService != null) {
            try {
                return mService.getArtistName();
            } catch (final RemoteException ignored) {
            }
        }
        return null;
    }

    public static String getAlbumName() {
        if (mService != null) {
            try {
                return mService.getAlbumName();
            } catch (final RemoteException ignored) {
            }
        }
        return null;
    }

    public static long getCurrentAlbumId() {
        if (mService != null) {
            try {
                return mService.getAlbumId();
            } catch (final RemoteException ignored) {
            }
        }
        return -1;
    }

    public static long getCurrentAudioId() {
        if (mService != null) {
            try {
                return mService.getAudioId();
            } catch (final RemoteException ignored) {
            }
        }
        return -1;
    }

    public static MusicPlaybackTrack getCurrentTrack() {
        if (mService != null) {
            try {
                return mService.getCurrentTrack();
            } catch (final RemoteException ignored) {
            }
        }
        return null;
    }

    public static MusicPlaybackTrack getTrack(int index) {
        if (mService != null) {
            try {
                return mService.getTrack(index);
            } catch (final RemoteException ignored) {
            }
        }
        return null;
    }

    public static long getNextAudioId() {
        if (mService != null) {
            try {
                return mService.getNextAudioId();
            } catch (final RemoteException ignored) {
            }
        }
        return -1;
    }

    public static long getPreviousAudioId() {
        if (mService != null) {
            try {
                return mService.getPreviousAudioId();
            } catch (final RemoteException ignored) {
            }
        }
        return -1;
    }

    public static long getCurrentArtistId() {
        if (mService != null) {
            try {
                return mService.getArtistId();
            } catch (final RemoteException ignored) {
            }
        }
        return -1;
    }

    public static int getAudioSessionId() {
        if (mService != null) {
            try {
                return mService.getAudioSessionId();
            } catch (final RemoteException ignored) {
            }
        }
        return -1;
    }

    public static long[] getQueue() {
        try {
            if (mService != null) {
                return mService.getQueue();
            } else {
            }
        } catch (final RemoteException ignored) {
        }
        return sEmptyList;
    }

    public static long getQueueItemAtPosition(int position) {
        try {
            if (mService != null) {
                return mService.getQueueItemAtPosition(position);
            } else {
            }
        } catch (final RemoteException ignored) {
        }
        return -1;
    }

    public static int getQueueSize() {
        try {
            if (mService != null) {
                return mService.getQueueSize();
            } else {
            }
        } catch (final RemoteException ignored) {
        }
        return 0;
    }

    public static int getQueuePosition() {
        try {
            if (mService != null) {
                return mService.getQueuePosition();
            }
        } catch (final RemoteException ignored) {
        }
        return 0;
    }

    public static void setQueuePosition(final int position) {
        if (mService != null) {
            try {
                mService.setQueuePosition(position);
            } catch (final RemoteException ignored) {
            }
        }
    }

    public static void refresh() {
        try {
            if (mService != null) {
                mService.refresh();
            }
        } catch (final RemoteException ignored) {
        }
    }

    public static int getQueueHistorySize() {
        if (mService != null) {
            try {
                return mService.getQueueHistorySize();
            } catch (final RemoteException ignored) {
            }
        }
        return 0;
    }

    public static int getQueueHistoryPosition(int position) {
        if (mService != null) {
            try {
                return mService.getQueueHistoryPosition(position);
            } catch (final RemoteException ignored) {
            }
        }
        return -1;
    }

    public static int[] getQueueHistoryList() {
        if (mService != null) {
            try {
                return mService.getQueueHistoryList();
            } catch (final RemoteException ignored) {
            }
        }
        return null;
    }

    public static int removeTrack(final long id) {
        try {
            if (mService != null) {
                return mService.removeTrack(id);
            }
        } catch (final RemoteException ingored) {
        }
        return 0;
    }

    public static boolean removeTrackAtPosition(final long id, final int position) {
        try {
            if (mService != null) {
                return mService.removeTrackAtPosition(id, position);
            }
        } catch (final RemoteException ingored) {
        }
        return false;
    }

    public static void moveQueueItem(final int from, final int to) {
        try {
            if (mService != null) {
                mService.moveQueueItem(from, to);
            } else {
            }
        } catch (final RemoteException ignored) {
        }
    }

    public static void playArtist(final Context context, final long artistId, int position, boolean shuffle) {
        final long[] artistList = getSongListForArtist(context, artistId);
        if (artistList != null) {
            playAll(context, artistList, position, artistId, ListenerUtil.IdType.Artist, shuffle);
        }
    }

    public static void playAlbum(final Context context, final long albumId, int position, boolean shuffle) {
        final long[] albumList = getSongListForAlbum(context, albumId);
        if (albumList != null) {
            playAll(context, albumList, position, albumId, ListenerUtil.IdType.Album, shuffle);
        }
    }

    public static void playAll(final Context context, final long[] list, int position,
                               final long sourceId, final ListenerUtil.IdType sourceType,
                               final boolean forceShuffle) {
        if (list == null || list.length == 0 || mService == null) {
            return;
        }
        try {
            if (forceShuffle) {
                mService.setShuffleMode(MusicService.SHUFFLE_NORMAL);
            }
            final long currentId = mService.getAudioId();
            final int currentQueuePosition = getQueuePosition();
            if (position != -1 && currentQueuePosition == position && currentId == list[position]) {
                final long[] playlist = getQueue();
                if (Arrays.equals(list, playlist)) {
                    mService.play();
                    return;
                }
            }
            if (position < 0) {
                position = 0;
            }
            mService.open(list, forceShuffle ? -1 : position, sourceId, sourceType.mId);
            mService.play();
        } catch (final RemoteException ignored) {
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在播放列表当前播放位置插入歌曲
     * @param context context to use
     * @param list songs to insert
     * @param sourceId source id
     * @param sourceType source type
     */
    public static void playNext(Context context, final long[] list, final long sourceId, final ListenerUtil.IdType sourceType) {
        if (mService == null) {
            return;
        }
        try {
            mService.enqueue(list, MusicService.NEXT, sourceId, sourceType.mId);
            Toast.makeText(context, R.string.set_play_next_success, Toast.LENGTH_SHORT).show();
        } catch (final RemoteException ignored) {
        }
    }

    /**
     * 本地所有歌曲随机播放
     * @param context
     */
    public static void shuffleAll(final Context context) {
        Cursor cursor = SongLoader.makeSongCursor(context, null, null);
        final long[] mTrackList = SongLoader.getSongListForCursor(cursor);
        final int position = 0;
        if (mTrackList.length == 0 || mService == null) {
            return;
        }
        try {
            mService.setShuffleMode(MusicService.SHUFFLE_NORMAL);
            final long mCurrentId = mService.getAudioId();
            final int mCurrentQueuePosition = getQueuePosition();
            if (mCurrentQueuePosition == position && mCurrentId == mTrackList[position]) {
                final long[] mPlaylist = getQueue();
                if (Arrays.equals(mPlaylist, mTrackList)) {
                    mService.play();
                    return;
                }
            }
            mService.open(mTrackList, -1, -1, ListenerUtil.IdType.NA.mId);
            mService.play();
            cursor.close();
            cursor = null;
        } catch (final RemoteException ignored) {
        }
    }

    /**
     * 获取歌手所有歌曲的ID
      * @param context context to use
     * @param id artist id
     * @return songs id
     */
    public static long[] getSongListForArtist(final Context context, final long id) {
        final String[] projection = new String[]{
                BaseColumns._ID
        };
        final String selection = MediaStore.Audio.AudioColumns.ARTIST_ID + "=" + id + " AND "
                + MediaStore.Audio.AudioColumns.IS_MUSIC + "=1";
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null,
                MediaStore.Audio.AudioColumns.ALBUM_KEY + "," + MediaStore.Audio.AudioColumns.TRACK);
        if (cursor != null) {
            final long[] mList = SongLoader.getSongListForCursor(cursor);
            cursor.close();
            cursor = null;
            return mList;
        }
        return sEmptyList;
    }

    /**
     * 获取专辑中所有曲目的ID
     * @param context context to use
     * @param id album id
     * @return songs id
     */
    public static long[] getSongListForAlbum(final Context context, final long id) {
        final String[] projection = new String[]{
                BaseColumns._ID
        };
        final String selection = MediaStore.Audio.AudioColumns.ALBUM_ID + "=" + id + " AND " + MediaStore.Audio.AudioColumns.IS_MUSIC
                + "=1";
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null,
                MediaStore.Audio.AudioColumns.TRACK + ", " + MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor != null) {
            final long[] mList = SongLoader.getSongListForCursor(cursor);
            cursor.close();
            cursor = null;
            return mList;
        }
        return sEmptyList;
    }

    public static int getSongCountForAlbumInt(final Context context, final long id) {
        int songCount = 0;
        if (id == -1) {
            return songCount;
        }

        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, id);
        Cursor cursor = context.getContentResolver().query(uri,
                new String[]{MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                if (!cursor.isNull(0)) {
                    songCount = cursor.getInt(0);
                }
            }
            cursor.close();
            cursor = null;
        }

        return songCount;
    }

    public static String getReleaseDateForAlbum(final Context context, final long id) {
        if (id == -1) {
            return null;
        }
        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, id);
        Cursor cursor = context.getContentResolver().query(uri, new String[]{
                MediaStore.Audio.AlbumColumns.FIRST_YEAR
        }, null, null, null);
        String releaseDate = null;
        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                releaseDate = cursor.getString(0);
            }
            cursor.close();
            cursor = null;
        }
        return releaseDate;
    }

    public static void seek(final long position) {
        if (mService != null) {
            try {
                mService.seek(position);
            } catch (final RemoteException ignored) {
            }
        }
    }

    public static void seekRelative(final long deltaInMs) {
        if (mService != null) {
            try {
                mService.seekRelative(deltaInMs);
            } catch (final RemoteException ignored) {
            } catch (final IllegalStateException ignored) {

            }
        }
    }

    public static long position() {
        if (mService != null) {
            try {
                return mService.position();
            } catch (final RemoteException ignored) {
            } catch (final IllegalStateException ex) {

            }
        }
        return 0;
    }

    public static long duration() {
        if (mService != null) {
            try {
                return mService.duration();
            } catch (final RemoteException ignored) {
            } catch (final IllegalStateException ignored) {

            }
        }
        return 0;
    }

    public static void clearQueue() {
        try {
            mService.removeTracks(0, Integer.MAX_VALUE);
        } catch (final RemoteException ignored) {
        }
    }

    /**
     * 在播放列表尾部插入歌曲
     * @param context context to use
     * @param list song list to insert
     * @param sourceId source id
     * @param sourceType source type
     */
    public static void addToQueue(final Context context, final long[] list, long sourceId,
                                  ListenerUtil.IdType sourceType) {
        if (mService == null) {
            return;
        }
        try {
            mService.enqueue(list, MusicService.LAST, sourceId, sourceType.mId);
            final String message = makeLabel(context, R.plurals.NNNtrackstoqueue, list.length);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } catch (final RemoteException ignored) {
        }
    }

    /**
     * 删除播放队列中歌曲
     * @param position song position in queue
     */
    public static void removeFromQueue(int position) {
        try {
            mService.removeTracks(position, position);
        } catch (final RemoteException ignored) {
        }
    }

    public static String makeLabel(final Context context, final int pluralInt,
                                   final int number) {
        return context.getResources().getQuantityString(pluralInt, number, number);
    }

    /**
     * 在歌单中批量增加曲目
     * @param context
     * @param ids
     * @param playlistid
     */
    public static void addToPlaylist(final Context context, final long[] ids, final long playlistid) {
        final int size = ids.length;
        final ContentResolver resolver = context.getContentResolver();
        final String[] projection = new String[]{
                "max(" + "play_order" + ")",
        };
        final Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistid);
        Cursor cursor = null;
        int base = 0;

        try {
            cursor = resolver.query(uri, projection, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                base = cursor.getInt(0) + 1;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }

        int numinserted = 0;
        for (int offSet = 0; offSet < size; offSet += 1000) {
            makeInsertItems(ids, offSet, 1000, base);
            numinserted += resolver.bulkInsert(uri, mContentValuesCache);
        }
        final String message = context.getResources().getQuantityString(
                R.plurals.NNNtrackstoplaylist, numinserted, numinserted);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void makeInsertItems(final long[] ids, final int offset, int len, final int base) {
        if (offset + len > ids.length) {
            len = ids.length - offset;
        }

        if (mContentValuesCache == null || mContentValuesCache.length != len) {
            mContentValuesCache = new ContentValues[len];
        }
        for (int i = 0; i < len; i++) {
            if (mContentValuesCache[i] == null) {
                mContentValuesCache[i] = new ContentValues();
            }
            mContentValuesCache[i].put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, base + offset + i);
            mContentValuesCache[i].put(MediaStore.Audio.Playlists.Members.AUDIO_ID, ids[offset + i]);
        }
    }

    /**
     * 创建歌单
     * @param context context to use
     * @param name playlist name
     * @return playlist id or -1 for fail
     */
    public static long createPlaylist(final Context context, final String name) {
        if (name != null && name.length() > 0) {
            final ContentResolver resolver = context.getContentResolver();
            final String[] projection = new String[]{
                    MediaStore.Audio.PlaylistsColumns.NAME
            };
            final String selection = MediaStore.Audio.PlaylistsColumns.NAME + " = '" + name + "'";
            Cursor cursor = resolver.query(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                    projection, selection, null, null);
            if (cursor.getCount() <= 0) {
                final ContentValues values = new ContentValues(1);
                values.put(MediaStore.Audio.PlaylistsColumns.NAME, name);
                final Uri uri = resolver.insert(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                        values);
                return Long.parseLong(uri.getLastPathSegment());
            }
            cursor.close();
            cursor = null;
            return -1;
        }
        return -1;
    }

    public static void renamePlaylist(final Context context, long playlistid, String newName) {
        final ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues(1);
        values.put(MediaStore.Audio.Playlists.NAME, newName);
        resolver.update(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                values, "_id=" + playlistid, null);
    }

    public static void openFile(final String path) {
        if (mService != null) {
            try {
                mService.openFile(path);
            } catch (final RemoteException ignored) {
            }
        }
    }

    public static final class ServiceBinder implements ServiceConnection {
        private final ServiceConnection mCallback;
        private final Context mContext;


        public ServiceBinder(final ServiceConnection callback, final Context context) {
            mCallback = callback;
            mContext = context;
        }

        @Override
        public void onServiceConnected(final ComponentName className, final IBinder service) {
            mService = IListenerService.Stub.asInterface(service);
            if (mCallback != null) {
                mCallback.onServiceConnected(className, service);
            }
            initPlaybackServiceWithSettings(mContext);
        }

        @Override
        public void onServiceDisconnected(final ComponentName className) {
            if (mCallback != null) {
                mCallback.onServiceDisconnected(className);
            }
            mService = null;
        }
    }

    public static final class ServiceToken {
        public ContextWrapper mWrappedContext;

        public ServiceToken(final ContextWrapper context) {
            mWrappedContext = context;
        }
    }
}
