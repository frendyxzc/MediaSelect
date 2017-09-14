package vip.frendy.media.presenters.interfaces;

import com.yalantis.ucrop.entity.LocalMedia;

import java.util.List;

/**
 * Created by iiMedia on 2017/3/1.
 */

public interface MediaSelectView {
    void onPictureSelectSuccess(List<LocalMedia> datas);
    void onVideoSelectSuccess(List<LocalMedia> datas);
}
