package org.yeewoe.listener.mvp.usecase;

import java.util.List;

import org.yeewoe.listener.mvp.model.Song;
import org.yeewoe.listener.respository.interfaces.Repository;
import rx.Observable;

/**
 * Created by yeewoe on 2016/12/3.
 */

public class GetAlbumSongs extends UseCase<GetAlbumSongs.RequestValues,GetAlbumSongs.ResponseValue>{

    private Repository mRepository;

    public GetAlbumSongs(Repository repository) {
        mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
        return new ResponseValue(mRepository.getSongsForAlbum(requestValues.getAlbumID()));
    }

    public static final class RequestValues implements UseCase.RequestValues{

        private long mAlbumID;

        public RequestValues(long albumID) {
            mAlbumID = albumID;
        }

        public long getAlbumID() {
            return mAlbumID;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Observable<List<Song>> mListObservable;

        public ResponseValue(Observable<List<Song>> listObservable) {
            mListObservable = listObservable;
        }

        public Observable<List<Song>> getSongList(){
            return mListObservable;
        }
    }
}
