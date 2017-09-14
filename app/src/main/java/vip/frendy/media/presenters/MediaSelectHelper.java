package vip.frendy.media.presenters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yalantis.ucrop.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import vip.frendy.media.R;
import vip.frendy.media.adapters.GridImageAdapter;
import vip.frendy.media.extensions.FullyGridLayoutManager;
import vip.frendy.media.presenters.interfaces.MediaSelectView;
import vip.frendy.mediasel.model.FunctionConfig;
import vip.frendy.mediasel.model.LocalMediaLoader;
import vip.frendy.mediasel.model.PictureConfig;

/**
 * Created by iiMedia on 2017/3/1.
 */

public class MediaSelectHelper extends Presenter {
    private Context mContext;

    private MediaSelectView mView;
    private RecyclerView mRPicture, mRVideo;
    private GridImageAdapter adapterPicture, adapterVideo;

    private List<LocalMedia> picturesSelected = new ArrayList<>();
    private List<LocalMedia> videosSelected = new ArrayList<>();

    private int DEFAULT_PICTURE_LIMIT = 9;
    private int DEFAULT_VIDEO_LIMIT = 1;
    private int DEFAULT_VIDEO_TIME_LIMIT = 10; // 视频秒数

    private int theme = 0;
    private int[] FOCUS_COLOR = {R.color.colorPrimary, R.color.colorPrimaryDark};

    public MediaSelectHelper(Context context, MediaSelectView view, RecyclerView picture, RecyclerView video) {
        mContext = context;
        mRPicture = picture;
        mRVideo = video;
        mView = view;

        //图片
        FullyGridLayoutManager managerPicture = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        mRPicture.setLayoutManager(managerPicture);
        adapterPicture = new GridImageAdapter(mContext, onPictureClickListener);
        adapterPicture.setSelectMax(DEFAULT_PICTURE_LIMIT);
        mRPicture.setAdapter(adapterPicture);
        //视频
        FullyGridLayoutManager managerVideo= new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        mRVideo.setLayoutManager(managerVideo);
        adapterVideo = new GridImageAdapter(mContext, onVideoClickListener);
        adapterVideo.setSelectMax(DEFAULT_VIDEO_LIMIT);
        mRVideo.setAdapter(adapterVideo);
    }

    /**
     * 图片监听器
     */
    public GridImageAdapter.onAddPicClickListener onPictureClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    // 进入相册
                    FunctionConfig config = new FunctionConfig();
                    config.setType(LocalMediaLoader.TYPE_IMAGE);
                    config.setCompress(true);
                    config.setEnablePixelCompress(true);
                    config.setEnableQualityCompress(true);
                    config.setMaxSelectNum(DEFAULT_PICTURE_LIMIT);
                    config.setSelectMode(FunctionConfig.MODE_MULTIPLE);
                    config.setShowCamera(true);
                    config.setEnablePreview(true);
                    config.setEnableCrop(false);
                    config.setPreviewVideo(true);
                    config.setRecordVideoDefinition(FunctionConfig.HIGH);// 视频清晰度
                    config.setRecordVideoSecond(DEFAULT_VIDEO_TIME_LIMIT);// 视频秒数
                    config.setCheckNumMode(false);
                    config.setCompressQuality(100);
                    config.setImageSpanCount(4);
                    config.setSelectMedia(picturesSelected);
                    config.setCompressFlag(2); //1:系统默认，2:luban
                    config.setThemeStyle(ContextCompat.getColor(mContext, FOCUS_COLOR[theme]));
                    // 可以自定义底部 预览 完成 文字的颜色和背景色
                    config.setPreviewColor(ContextCompat.getColor(mContext, android.R.color.white));
                    config.setCompleteColor(ContextCompat.getColor(mContext, android.R.color.white));
                    config.setPreviewBottomBgColor(ContextCompat.getColor(mContext, FOCUS_COLOR[theme]));
                    config.setBottomBgColor(ContextCompat.getColor(mContext, FOCUS_COLOR[theme]));

                    // 先初始化参数配置，在启动相册
                    PictureConfig.init(config);
                    PictureConfig.getPictureConfig().openPhoto(mContext, pictureCallback);
                    break;

                case 1:
                    // 删除图片
                    picturesSelected.remove(position);
                    adapterPicture.notifyItemRemoved(position);
                    break;
            }
        }
    };

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback pictureCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            picturesSelected = resultList;
            Log.i("callBack_result_picture", picturesSelected.size() + "");
            if (picturesSelected != null) {
                adapterPicture.setList(picturesSelected);
                adapterPicture.notifyDataSetChanged();
            }
            mView.onPictureSelectSuccess(picturesSelected);
        }
    };


    /**
     * 视频监听器
     */
    private GridImageAdapter.onAddPicClickListener onVideoClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    // 进入相册
                    FunctionConfig config = new FunctionConfig();
                    config.setType(LocalMediaLoader.TYPE_VIDEO);
                    config.setCompress(true);
                    config.setEnablePixelCompress(true);
                    config.setEnableQualityCompress(true);
                    config.setMaxSelectNum(DEFAULT_VIDEO_LIMIT);
                    config.setSelectMode(FunctionConfig.MODE_SINGLE);
                    config.setShowCamera(true);
                    config.setEnablePreview(true);
                    config.setEnableCrop(false);
                    config.setPreviewVideo(true);
                    config.setRecordVideoDefinition(FunctionConfig.HIGH);// 视频清晰度
                    config.setRecordVideoSecond(DEFAULT_VIDEO_TIME_LIMIT);// 视频秒数
                    config.setCheckNumMode(false);
                    config.setCompressQuality(100);
                    config.setImageSpanCount(4);
                    config.setSelectMedia(videosSelected);
                    config.setCompressFlag(2); //1:系统默认，2:luban
                    config.setThemeStyle(ContextCompat.getColor(mContext, FOCUS_COLOR[theme]));
                    // 可以自定义底部 预览 完成 文字的颜色和背景色
                    config.setPreviewColor(ContextCompat.getColor(mContext, android.R.color.white));
                    config.setCompleteColor(ContextCompat.getColor(mContext, android.R.color.white));
                    config.setPreviewBottomBgColor(ContextCompat.getColor(mContext, FOCUS_COLOR[theme]));
                    config.setBottomBgColor(ContextCompat.getColor(mContext, FOCUS_COLOR[theme]));

                    // 先初始化参数配置，在启动相册
                    PictureConfig.init(config);
                    PictureConfig.getPictureConfig().openPhoto(mContext, videoCallback);
                    break;

                case 1:
                    // 删除图片
                    videosSelected.remove(position);
                    adapterVideo.notifyItemRemoved(position);
                    break;
            }
        }
    };

    /**
     * 视频回调方法
     */
    private PictureConfig.OnSelectResultCallback videoCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            videosSelected = resultList;
            Log.i("callBack_result_video", videosSelected.size() + "");
            if (videosSelected != null) {
                adapterVideo.setList(videosSelected);
                adapterVideo.notifyDataSetChanged();
            }
            mView.onVideoSelectSuccess(videosSelected);
        }
    };

    @Override
    public void onDestory() {
        mContext = null;
        mRPicture = null;
        mRVideo = null;
    }
}
