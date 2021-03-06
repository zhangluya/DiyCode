package com.zly.diycode.data.topics;


import com.zly.diycode.data.Callback;
import com.zly.diycode.reply.Node;

import java.util.List;
import java.util.Map;

import static com.zly.diycode.topics.EntitiesContract.Reply;
import static com.zly.diycode.topics.EntitiesContract.Topics;

/**
 * Created by zhangluya on 2017/3/22.
 */

public interface TopicsData {

    void getTopics(Map<String, Object> params, Callback<List<Topics>> callback);

    void getTopics(String nodeId, String limit, Callback<List<Topics>> callback);

    void create(Map<String, Object> params, Callback<Topics> callback);

    void create(String nodeId, String title, String body, Callback<Topics> callback);

    void update(String id, Map<String, Object> params, Callback<Topics> callback);

    void delete(String id, Callback<Boolean> callback);

    void getById(String id, Callback<Topics> callback);

    void ban(String id, Callback<Boolean> callback);

    void favorite(String id, Callback<Boolean> callback);

    void unFavorite(String id, Callback<Boolean> callback);

    void follow(String id, Callback<Boolean> callback);

    void unFollow(String id, Callback<Boolean> callback);

    void getReplies(String id, Map<String, Object> params, Callback<List<Reply>> callback);

    void getReplies(String id, int offset, Callback<List<Reply>> callback);

    void addReplies(String topicsId, String body, Callback<Reply> callback);

    void getNodes(Callback<List<Node>> nodes);
}
