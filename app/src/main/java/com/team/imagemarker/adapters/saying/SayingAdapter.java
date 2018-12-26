package com.team.imagemarker.adapters.saying;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.saying.SayingModel;
import com.team.imagemarker.utils.CircleImageView;

import java.util.List;


/**
 * Created by Lmy on 2017/4/30.
 * email 1434117404@qq.com
 */

public class SayingAdapter extends BaseAdapter {
    private Context context;
    private List<SayingModel> list;
    ViewHolder viewHolder = null;

    public SayingAdapter(Context context, List<SayingModel> list) {
        this.context = context;
        this.list = list;
    }

    btnClickListener listener;

    public interface btnClickListener {
        public void btnDeleteClick(int position);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_saying_scan, parent, false);
            viewHolder.userHeadImg = (CircleImageView) convertView.findViewById(R.id.user_img);
            viewHolder.userNickName = (TextView) convertView.findViewById(R.id.user_nick_name);
            viewHolder.sendTime = (TextView) convertView.findViewById(R.id.send_time);
            viewHolder.deleteSaying = (ImageView) convertView.findViewById(R.id.delete_saying);
            viewHolder.sayingContent = (TextView) convertView.findViewById(R.id.saying_content);
            viewHolder.gridView = (GridView) convertView.findViewById(R.id.saying_imgs);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SayingModel sayingBean = list.get(position);
//        Log.e("tag", "用户头像地址: " + sayingBean.getUserHeadImg());
//        getImg(sayingBean.getUserHeadImg());
        viewHolder.userHeadImg.setImageResource(R.mipmap.image1);

//        Glide.with(context)
//                .load(sayingBean.getUserHeadImg())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into((viewHolder.userHeadImg);

//        viewHolder.userNickName.setText(sayingBean.getNickName());
//        viewHolder.sendTime.setText("发布时间: " + sayingBean.getSendTime());
//        viewHolder.sayingContent.setText(sayingBean.getSayingContent());
//        if(sayingBean.getUserId() == Constants.USER_ID){
//            viewHolder.deleteSaying.setVisibility(View.VISIBLE);
//            viewHolder.deleteSaying.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.btnDeleteClick(position);
//                }
//            });
//        }else{
//            viewHolder.deleteSaying.setVisibility(View.GONE);
//        }
//
//        List<String> mList = new ArrayList<String>();
//        if(sayingBean.getSayingImg1() != null && !sayingBean.getSayingImg1().equals("")){
//            mList.add(sayingBean.getSayingImg1());
//        }
//        if(sayingBean.getSayingImg3() != null && !sayingBean.getSayingImg2().equals("")){
//            mList.add(sayingBean.getSayingImg3());
//        }
//        if(sayingBean.getSayingImg3() != null && !sayingBean.getSayingImg3().equals("")){
//            mList.add(sayingBean.getSayingImg3());
//        }
//        if(sayingBean.getSayingImg4() != null && !sayingBean.getSayingImg4().equals("")){
//            mList.add(sayingBean.getSayingImg4());
//            ViewGroup.MarginLayoutParams marginLayoutParams=new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,620);
//            marginLayoutParams.setMargins(marginLayoutParams.leftMargin+50, 5, marginLayoutParams.rightMargin+50, marginLayoutParams.bottomMargin);
//            viewHolder.gridView.setLayoutParams(new RelativeLayout.LayoutParams(marginLayoutParams));
//        }
//        if(sayingBean.getSayingImg5() != null && !sayingBean.getSayingImg5().equals("")){
//            mList.add(sayingBean.getSayingImg5());
//        }
//        if(sayingBean.getSayingImg6() != null && !sayingBean.getSayingImg6().equals("")){
//            mList.add(sayingBean.getSayingImg6());
//        }

//        if(sayingBean.getSayingImg1() ==null && sayingBean.getSayingImg4().equals("")){
//            ViewGroup.MarginLayoutParams marginLayoutParams=new ViewGroup.MarginLayoutParams(viewHolder.gridView.getLayoutParams().width,0);
//            marginLayoutParams.setMargins(marginLayoutParams.leftMargin+50, 5, marginLayoutParams.rightMargin+50, marginLayoutParams.bottomMargin);
//            viewHolder.gridView.setLayoutParams(new RelativeLayout.LayoutParams(marginLayoutParams));
//        }
//        if(sayingBean.getSayingImg4() == null && sayingBean.getSayingImg4().equals("")){
//            ViewGroup.MarginLayoutParams marginLayoutParams=new ViewGroup.MarginLayoutParams(viewHolder.gridView.getLayoutParams().width,300);
//            marginLayoutParams.setMargins(marginLayoutParams.leftMargin+50, 5, marginLayoutParams.rightMargin+50, marginLayoutParams.bottomMargin);
//            viewHolder.gridView.setLayoutParams(new RelativeLayout.LayoutParams(marginLayoutParams));
//        }
//        if(mList!=null&&mList.size()<=3){
//            ViewGroup.MarginLayoutParams marginLayoutParams=new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);
//            marginLayoutParams.setMargins(marginLayoutParams.leftMargin+50, 5, marginLayoutParams.rightMargin+50, marginLayoutParams.bottomMargin);
//            viewHolder.gridView.setLayoutParams(new RelativeLayout.LayoutParams(marginLayoutParams));
//        }
//        if (mList != null && mList.size() != 0) {
//            viewHolder.gridView.setAdapter(new SayingGradAdapter(context, mList));
//        } else {
//            viewHolder.gridView.setVisibility(View.GONE);
//        }
        return convertView;
    }

    private void setBitmap(Bitmap bitmap) {

    }

    class ViewHolder{
        CircleImageView userHeadImg;
        TextView userNickName;
        TextView sendTime;
        ImageView deleteSaying;
        TextView sayingContent;
        GridView gridView;
    }

}
