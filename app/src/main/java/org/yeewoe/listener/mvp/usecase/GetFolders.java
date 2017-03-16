package org.yeewoe.listener.mvp.usecase;

import java.util.List;

import org.yeewoe.listener.mvp.model.FolderInfo;
import org.yeewoe.listener.respository.interfaces.Repository;
import rx.Observable;

/**
 * Created by yeewoe on 2016/12/11.
 */

public class GetFolders extends UseCase<GetFolders.RequestValues,GetFolders.ResponseValue>{

    private final Repository mRepository;

    public GetFolders(Repository repository) {
        this.mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
        return new ResponseValue(mRepository.getFoldersWithSong());
    }

    public static final class RequestValues implements UseCase.RequestValues{
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Observable<List<FolderInfo>> mListObservable;

        public ResponseValue(Observable<List<FolderInfo>> listObservable) {
            mListObservable = listObservable;
        }

        public Observable<List<FolderInfo>> getFolderList(){
            return mListObservable;
        }
    }
}
