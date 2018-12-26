package com.team.imagemarker.activitys.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.mark.MarkHomeActivity;
import com.team.imagemarker.bases.BaseActivity;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.entitys.MarkerModel;
import com.team.imagemarker.entitys.home.CateGoryInfo;
import com.team.imagemarker.entitys.marker.ItemEntity;
import com.team.imagemarker.utils.EditTextWithDel;
import com.team.imagemarker.utils.RoundAngleImageView;
import com.team.imagemarker.utils.ToastUtil;
import com.team.imagemarker.utils.tag.TagColor;
import com.team.imagemarker.utils.tag.TagGroup;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team.imagemarker.R.id.to_search;

/**
 * Created by Lmy on 2017/5/4.
 * email 1434117404@qq.com
 */

public class UserSearchActivity extends BaseActivity implements View.OnClickListener{
    private TagGroup tagGroup;
    private List<String> tagList = new ArrayList<String>();
    private int hotIndex = 0;

    private EditTextWithDel searchContent;//搜索内容
    private ListView showResult;//显示结果列表
    private ListView showHistory;//显示历史记录列表
    private RelativeLayout layoutHotWord;//热门搜索
    private RelativeLayout rlHistory;//历史记录
    private ArrayAdapter<String> historyAdapter;//历史记录
    private List<CateGoryInfo> resultList = new ArrayList<>();
    private List<ItemEntity> itemModel;
    private List<MarkerModel> list = new ArrayList<>();
    private ResultListAdapter adapter = new ResultListAdapter();
    private ToastUtil toastUtil = new ToastUtil();
    private TextView searchBack, tvClear, tvChangeWords, toSearch;//清除历史记录
    private int[] imgs = {R.mipmap.search1, R.mipmap.search2, R.mipmap.search3, R.mipmap.search4, R.mipmap.search5, R.mipmap.search6};

    private static final int DO_SEARCH = 1;
    private static final int GO_LINE = 2;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DO_SEARCH:{
                    StartSearch((String) msg.obj);
                }
                break;
                case GO_LINE:{
                    if(list .size() != 0){
                        if(showResult.getVisibility() == View.GONE){
                            showResult.setVisibility(View.VISIBLE);
                        }
                        showResult.setAdapter(adapter);
                        showResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Intent intent = new Intent(UserSearchActivity.this, MarkHomeActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("pageTag", "searchHobby");
//                                bundle.putSerializable("item", (Serializable) itemModel);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                Intent intent = new Intent(UserSearchActivity.this, MarkHomeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("pageTag", "imgNavPage");
                                bundle.putSerializable("item", list.get(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                                UserSearchActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                        });
                    }else{
                        toastUtil.Short(UserSearchActivity.this, "没有相关内容,换换其他搜索词吧").show();
                    }
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        tagGroup = (TagGroup) findViewById(R.id.tag_group);
        tagList.add("好吃的");
        tagList.add("优美的风景");
        tagList.add("蓝天");
        tagList.add("高达的建筑");
        tagList.add("旅游景点");
        tagList.add("宠物");
        tagList.add("文具-书包");
        tagList.add("自由女神");
        showHotWord(tagList);
        bindView();
    }

    private void bindView() {
        searchContent = (EditTextWithDel) findViewById(R.id.user_search);
        showResult = (ListView) findViewById(R.id.lvShowResult);
        showHistory = (ListView) findViewById(R.id.lvSearchHistory);
        layoutHotWord = (RelativeLayout) findViewById(R.id.layoutHotWord);
        rlHistory = (RelativeLayout) findViewById(R.id.rlHistory);
        searchBack = (TextView) findViewById(R.id.search_back);
        toSearch = (TextView) findViewById(to_search);
        tvClear = (TextView) findViewById(R.id.tvClear);
        tvChangeWords = (TextView) findViewById(R.id.tvChangeWords);

        searchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){//搜索框为空时，结果列表隐藏
                    list.clear();
                    adapter.notifyDataSetChanged();
                    showResult.setVisibility(View.GONE);
                }else{
                    showResult.setVisibility(View.VISIBLE);
                }
//                mHandler.sendEmptyMessageDelayed(DO_SEARCH,1000);//延迟一秒进行搜索，提高性能，节约流量
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    showResult.setVisibility(View.GONE);
                    layoutHotWord.setVisibility(View.VISIBLE);
                    rlHistory.setVisibility(View.VISIBLE);
                }
            }
        });
        initSearchHistory();

        searchBack.setOnClickListener(this);
        toSearch.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvChangeWords.setOnClickListener(this);
    }

    /**
     * 联网进行实时搜索
     */
    private void StartSearch(final String searchContent) {
        if(list.size() != 0){
            list.clear();
            adapter.notifyDataSetChanged();
        }
        String url = Constants.Hobby_Nav_Free_Search;
        Map<String, String> map = new HashMap<String, String>();
        map.put("search", searchContent);
        VolleyRequestUtil.RequestPost(this, url, "freeSearch", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "onSuccess: " + result);
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.optJSONArray("picture");
                    Gson gson = null;
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.optJSONObject(i);
                        gson = new Gson();
                        MarkerModel model = gson.fromJson(object1.toString(), MarkerModel.class);
                        list.add(model);
                    }
//                    setHobbyDate(hobbyPushList);
                    mHandler.sendEmptyMessage(GO_LINE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("FirstPageFragment", "onError: HobbyPushGetData:" + error.toString());
                StartSearch(searchContent);
            }
        });

    }

    /**
     * 初始化历史记录列表
     */
    private void initSearchHistory() {
        String cache = SearchHistoryCacheUtils.getCache(this);
        if(cache != null){
            List<String> historyRecordList = new ArrayList<>();
            for (String record : cache.split(",")){
                historyRecordList.add(record);
            }
            historyAdapter = new ArrayAdapter<String>(this, R.layout.item_search_history, historyRecordList);
            if(historyRecordList.size() > 0){
                showHistory.setAdapter(historyAdapter);
            }
        }
    }

    /**
     * 保存历史记录
     */
    private void saveHistory(String history){
        String oldCache = SearchHistoryCacheUtils.getCache(this);
        StringBuilder builder = new StringBuilder(history);
        if(oldCache == null){
            SearchHistoryCacheUtils.setCache(builder.toString(), this);
            updateHistory();//更新历史记录
        }else{
            builder.append("," + oldCache);
            if(!oldCache.contains(history)){//避免缓存重复数据
                SearchHistoryCacheUtils.setCache(builder.toString(), this);
                updateHistory();
            }
        }
    }

    /**
     * 更新历史记录
     */
    private void updateHistory() {
        String cache = SearchHistoryCacheUtils.getCache(this);
        String[] recordData = new String[]{};
        if(cache != null){
            recordData = cache.split(",");
        }
        historyAdapter = new ArrayAdapter<String>(this, R.layout.item_search_history, recordData);
        showHistory.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
    }

    /**
     * 显示热门标签
     */
    private void showHotWord(List<String> list) {
        int tagSize = 8;//每次显示8个
        String[] tags = new String[tagSize];
        for (int j = 0; j < tagSize && j < list.size(); hotIndex++, j++) {
            tags[j] = list.get(hotIndex % list.size());
        }
        List<TagColor> colors = TagColor.getRandomColors(tagSize);
        tagGroup.setTags(colors, tags);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_back:{
                onBackPressed();
            }
            break;
            case R.id.tvClear:{//删除历史记录
                String cache = SearchHistoryCacheUtils.getCache(this);
                if(cache != null){
                    SearchHistoryCacheUtils.setCache("", this);
                    updateHistory();
                }
            }
            break;
            case R.id.tvChangeWords:{
                List<String> list = new ArrayList<>();
                list.add("旅游景点");
                list.add("宠物");
                list.add("文具-书包");
                list.add("自由女神");
                list.add("好吃的");
                list.add("优美的风景");
                list.add("蓝天");
                list.add("高达的建筑");
                showHotWord(list);
            }
            break;
            case R.id.to_search:{
//                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                String content = searchContent.getText().toString();
                if(TextUtils.isEmpty(content)){
                    Toast.makeText(this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                layoutHotWord.setVisibility(View.GONE);
                rlHistory.setVisibility(View.GONE);
                saveHistory(content);//缓存搜索内容
                Message message = new Message();
                message.what = DO_SEARCH;
                message.obj = content;
                mHandler.sendMessage(message);
            }
            break;
        }
    }

    class ResultListAdapter extends BaseAdapter{

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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(UserSearchActivity.this, R.layout.item_search_recult, null);
                viewHolder = new ViewHolder();
                viewHolder.itemImg = (RoundAngleImageView) convertView.findViewById(R.id.item_img);
                viewHolder.itemName = (TextView) convertView.findViewById(R.id.item_name);
                viewHolder.itemTime = (TextView) convertView.findViewById(R.id.item_time);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            MarkerModel info = list.get(position);
            Glide.with(UserSearchActivity.this)
                    .load(info.getImageUrl1())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.8f)
                    .into(viewHolder.itemImg);
//            viewHolder.itemImg.setImageResource(imgs[position]);
//            viewHolder.itemName.setText(info.getName());
//            viewHolder.itemTime.setText(info.getMainTitle());
            return convertView;
        }
    }

    class ViewHolder{
        public RoundAngleImageView itemImg;
        public TextView itemName;
        public TextView itemTime;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}