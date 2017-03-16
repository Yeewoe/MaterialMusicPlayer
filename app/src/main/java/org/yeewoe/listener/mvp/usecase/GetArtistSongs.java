package org.yeewoe.listener.mvp.usecase;

import java.util.List;

import org.yeewoe.listener.mvp.model.Song;
import org.yeewoe.listener.respository.interfaces.Repository;
import rx.Observable;

/**
 * Created by yeewoe on 2016/11/25.
 */

public class GetArtistSongs extends UseCase<GetArtistSongs.RequestValues,GetArtistSongs.ResponseValue>{

    private Repository mRepository;

    public GetArtistSongs(Repository repository) {
        mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
        return new ResponseValue(mRepository.getSongsForArtist(requestValues.getArtistID()));
    }

    public static final class RequestValues implements UseCase.RequestValues{

        private long mArtistID;

        public RequestValues(long artistID) {
            mArtistID = artistID;
        }

        public long getArtistID() {
            return mArtistID;
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
