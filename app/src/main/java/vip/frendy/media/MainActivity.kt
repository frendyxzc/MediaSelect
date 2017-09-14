package vip.frendy.media

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.yalantis.ucrop.entity.LocalMedia
import vip.frendy.media.presenters.MediaSelectHelper
import vip.frendy.media.presenters.interfaces.MediaSelectView

/**
 * Created by iiMedia on 2017/9/14.
 */
class MainActivity: AppCompatActivity(), MediaSelectView {
    private val mContext = this
    private var mediaSelectHelper: MediaSelectHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaSelectHelper = MediaSelectHelper(mContext, this,
                findViewById(R.id.recycler_picture) as RecyclerView,
                findViewById(R.id.recycler_video) as RecyclerView)
    }

    override fun onPictureSelectSuccess(datas: MutableList<LocalMedia>?) {

    }

    override fun onVideoSelectSuccess(datas: MutableList<LocalMedia>?) {

    }

    override fun onDestroy() {
        mediaSelectHelper?.onDestory()
        super.onDestroy()
    }
}