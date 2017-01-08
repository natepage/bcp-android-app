package com.bowling_club_poitevin.bowlingclubpoitevin.utils.website;

import android.util.Log;

import com.bowling_club_poitevin.bowlingclubpoitevin.utils.InputStreamReaderWithBuffer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BcpDataProvider {

    private static final String _baseUrl = "http://bowling-poitiers.com";
    private static final String _apiJsonUrl = _baseUrl + "/api_rest/json";
    private static final String _getPostsListUrl = _apiJsonUrl + "/get/posts/";
    private static final String _getPostUrl = _apiJsonUrl + "/get/post/";
    private static final String _getImageUrl = _apiJsonUrl + "/get/image/%s/%s";
    private static final String _sharePostUrl = _baseUrl + "/post/";

    private static SimpleDateFormat _dateParser = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

    public static BcpPostsList getPostsList(int page) {
        try {
            Log.i("DownloadPosts", "Start downloading...");
            Log.i("DownloadPosts", _getPostsListUrl + page);

            JSONObject jsonObject = new JSONObject(getContentFromUrl(_getPostsListUrl + page));

            if(jsonObject.getJSONArray("posts").length() > 0){
                JSONArray jsonPosts = jsonObject.getJSONArray("posts");
                List<BcpPost> postsList = new ArrayList<>(jsonPosts.length());
                for (int i = 0; i < jsonPosts.length(); i++) {
                    //Post
                    BcpPost post = bindJsonToPost(jsonPosts.getJSONObject(i));

                    postsList.add(post);
                }

                Log.i("DownloadPosts", "Download finished!");

                return new BcpPostsList(postsList, jsonObject.getBoolean("has_more"));
            } else {
                return null;
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            Log.i("DownloadPosts." + e.getClass().toString(), e.getMessage());
            return null;
        }
    }

    public static BcpPost getPostBySlug(String slug) {
        try {
            JSONObject jsonPost = new JSONObject(getContentFromUrl(_getPostUrl + slug));

            return bindJsonToPost(jsonPost);
        } catch (Exception e){
            return null;
        }
    }

    public static String getImageFullWebPath(String filter, String webPath) {
        try {
            JSONObject image = new JSONObject(getContentFromUrl(String.format(_getImageUrl, filter, webPath)));

            return image.getString("cache_path");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSharePostUrl(String slug) {
        return _sharePostUrl + slug;
    }

    private static String getContentFromUrl(String urlContent) {
        try {
            URL url = new URL(urlContent);

            return InputStreamReaderWithBuffer.getInputStreamContent(url.openStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static BcpPost bindJsonToPost(JSONObject jsonPost) {
        BcpPost post = new BcpPost();

        try {
            if(!jsonPost.getString("created").equals("null")) {
                post.set_created(_dateParser.parse(jsonPost.getString("created")));
            }
            if(!jsonPost.getString("updated").equals("null")) {
                post.set_created(_dateParser.parse(jsonPost.getString("updated")));
            }

            post
                    .set_authorName(jsonPost.getString("author_name"))
                    .set_published(jsonPost.getBoolean("published"))
                    .set_title(jsonPost.getString("title"))
                    .set_slug(jsonPost.getString("slug"))
                    .set_description(jsonPost.getString("description"))
                    .set_content(jsonPost.getString("content"))
                    .set_previewImageKey(jsonPost.getInt("preview_image_key") != -1 ? jsonPost.getInt("preview_image_key") : 0)
            ;

            //Post's categories
            List<BcpCategory> bcpCategories = new ArrayList<>();
            if(jsonPost.getJSONArray("categories").length() > 0){
                JSONArray jsonCategories = jsonPost.getJSONArray("categories");

                for (int j = 0; j < jsonCategories.length(); j++) {
                    JSONObject jsonCategory = jsonCategories.getJSONObject(j);
                    BcpCategory category = new BcpCategory();

                    category
                            .set_title(jsonCategory.getString("title"))
                            .set_slug(jsonCategory.getString("slug"))
                    ;

                    bcpCategories.add(category);
                }
            }
            post.set_categories(bcpCategories);

            //Post's images
            List<BcpImage> bcpImages = new ArrayList<>();
            if(jsonPost.getJSONArray("images").length() > 0){
                JSONArray jsonImages = jsonPost.getJSONArray("images");

                for(int k = 0; k < jsonImages.length(); k++) {
                    JSONObject jsonImage = jsonImages.getJSONObject(k);
                    BcpImage image = new BcpImage();

                    image.set_alt(jsonImage.getString("alt"));

                    JSONObject imageWebpaths = jsonImage.getJSONObject("web_paths");
                    Map<String, String> webPaths = new HashMap<>();
                    webPaths.put("post_preview", imageWebpaths.getString("post_preview"));
                    webPaths.put("post_view", imageWebpaths.getString("post_view"));

                    image.set_webPaths(webPaths);

                    bcpImages.add(image);
                }
            }
            post.set_images(bcpImages);

            //Post's pdfs
            List<BcpPdf> bcpPdfs = new ArrayList<>();
            if(jsonPost.getJSONArray("pdfs").length() > 0){
                JSONArray jsonPdfs = jsonPost.getJSONArray("pdfs");

                for(int l = 0; l < jsonPdfs.length(); l++){
                    JSONObject jsonPdf = jsonPdfs.getJSONObject(l);
                    BcpPdf pdf = new BcpPdf();

                    pdf
                        .set_alt(jsonPdf.getString("alt"))
                        .set_webPath(jsonPdf.getString("web_path"))
                    ;

                    bcpPdfs.add(pdf);
                }
            }
            post.set_pdfs(bcpPdfs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return post;
    }
}
