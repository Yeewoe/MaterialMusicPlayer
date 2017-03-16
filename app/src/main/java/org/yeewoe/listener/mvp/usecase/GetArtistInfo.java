package org.yeewoe.listener.mvp.usecase;

import org.yeewoe.listener.api.model.ArtistInfo;
import org.yeewoe.listener.respository.interfaces.Repository;
import rx.Observable;

/**
 * Created by yeewoe on 2016/11/13.
 */

public class GetArtistInfo extends UseCase<GetArtistInfo.RequestValues,GetArtistInfo.ResponseValue>{

    private final Repository mRepository;

    public GetArtistInfo(Repository repository) {
        mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
        return new ResponseValue(mRepository.getArtistInfo(requestValues.getArtistName()));
    }

    public static final class RequestValues implements UseCase.RequestValues{

        private final String artistName;

        public RequestValues(String name){
            artistName = name;
        }

        public String getArtistName() {
            return artistName;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Observable<ArtistInfo> mArtistInfoObservable;

        public ResponseValue(Observable<ArtistInfo> artistInfoObservable) {
            mArtistInfoObservable = artistInfoObservable;
        }

        public Observable<ArtistInfo> getArtistInfo(){
            return mArtistInfoObservable;
        }
    }
}
