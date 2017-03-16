package org.yeewoe.listener.mvp.usecase;

import java.util.List;

import org.yeewoe.listener.mvp.model.Song;
import org.yeewoe.listener.respository.interfaces.Repository;
import rx.Observable;

/**
 * Created by yeewoe on 2016/12/12.
 */

public class GetFolderSongs extends UseCase<GetFolderSongs.RequestValues,GetFolderSongs.ResponseValue>{

    private final Repository mRepository;

    public GetFolderSongs(Repository repository) {
        this.mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
        return new ResponseValue(mRepository.getSongsInFolder(requestValues.getPath()));
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String path;

        public RequestValues(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
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
