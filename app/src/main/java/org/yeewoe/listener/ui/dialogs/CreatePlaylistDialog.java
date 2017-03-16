package org.yeewoe.listener.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.yeewoe.listener.MusicPlayer;
import org.yeewoe.listener.R;
import org.yeewoe.listener.RxBus;
import org.yeewoe.listener.event.PlaylistUpdateEvent;
import org.yeewoe.listener.mvp.model.Song;

/**
 * Created by naman on 20/12/15.
 */
public class CreatePlaylistDialog extends DialogFragment {

    public static CreatePlaylistDialog newInstance() {
        return newInstance((Song) null);
    }

    public static CreatePlaylistDialog newInstance(Song song) {
        long[] songs;
        if (song == null) {
            songs = new long[0];
        } else {
            songs = new long[1];
            songs[0] = song.id;
        }
        return newInstance(songs);
    }

    public static CreatePlaylistDialog newInstance(long[] songList) {
        CreatePlaylistDialog dialog = new CreatePlaylistDialog();
        Bundle bundle = new Bundle();
        bundle.putLongArray("songs", songList);
        dialog.setArguments(bundle);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MaterialDialog.Builder(getActivity())
                .title(R.string.create_new_playlist)
                .positiveText(R.string.create)
                .negativeText(R.string.cancel)
                .input(getString(R.string.playlist_name), "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                        long[] songs = getArguments().getLongArray("songs");
                        long playistId = MusicPlayer.createPlaylist(getActivity(), input.toString());

                        if (playistId != -1) {
                            if (songs != null && songs.length != 0) {
                                MusicPlayer.addToPlaylist(getActivity(), songs, playistId);
                            } else {
                                Toast.makeText(getActivity(), R.string.create_playlist_success, Toast.LENGTH_SHORT).show();
                            }
                            RxBus.getInstance().post(new PlaylistUpdateEvent());
                        } else {
                            Toast.makeText(getActivity(), R.string.create_playlist_fail, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).build();
    }
}
