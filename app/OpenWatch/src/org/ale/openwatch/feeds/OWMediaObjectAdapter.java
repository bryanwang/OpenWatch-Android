package org.ale.openwatch.feeds;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.DateUtils;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.ale.openwatch.R;
import org.ale.openwatch.constants.Constants;
import org.ale.openwatch.constants.DBConstants;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OWMediaObjectAdapter extends SimpleCursorAdapter {

    // A transform filter that simply returns just the text captured by the
    // first regular expression group.
    Linkify.TransformFilter mentionFilter = new Linkify.TransformFilter() {
        public final String transformUrl(final Matcher match, String url) {
            return match.group(1);
        }
    };

    // Match @mentions and capture just the username portion of the text.
    Pattern pattern = Pattern.compile("#([A-Za-z0-9_-]+)");
    String scheme = "openwatch://openwatch.net/w/";

	public OWMediaObjectAdapter(Context context, Cursor c) {
		super(context, R.layout.fancy_remote_feed_item, c, new String[]{}, new int[]{},0);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor){
		//super.bindView(view, context, cursor);
		
		ViewCache view_cache = (ViewCache) view.getTag(R.id.list_item_cache);
        if (view_cache == null) {
        	view_cache = new ViewCache();
        	view_cache.title = (TextView) view.findViewById(R.id.title);
        	view_cache.username = (TextView) view.findViewById(R.id.username);
        	view_cache.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        	view_cache.members = (TextView) view.findViewById(R.id.members);
        	view_cache.submissions = (TextView) view.findViewById(R.id.submissions);
            view_cache.iconContainer = (LinearLayout) view.findViewById(R.id.iconContainer);
            //view_cache.typeIcon = (ImageView) view.findViewById(R.id.type_icon);
            view_cache.playButton = (ImageView) view.findViewById(R.id.playButton);
            view_cache.progressBar = (ProgressBar) view.findViewById(R.id.videoProgress);
            view_cache.userThumbnail = (ImageView) view.findViewById(R.id.userImage);
            view_cache.lastEdited = (TextView) view.findViewById(R.id.date);
            view_cache.location = (TextView) view.findViewById(R.id.location);
            
        	view_cache.title_col = cursor.getColumnIndexOrThrow(DBConstants.RECORDINGS_TABLE_TITLE);   
        	view_cache.username_col = cursor.getColumnIndexOrThrow(DBConstants.RECORDINGS_TABLE_USERNAME);   
        	view_cache.thumbnail_col = cursor.getColumnIndexOrThrow(DBConstants.RECORDINGS_TABLE_THUMB_URL);
        	view_cache.members_col = cursor.getColumnIndex("members");
        	view_cache.submissions_col = cursor.getColumnIndex("submissions");
            view_cache.expires_col = cursor.getColumnIndex("expires");
            view_cache.audio_col = cursor.getColumnIndexOrThrow(DBConstants.MEDIA_OBJECT_AUDIO);
            view_cache.photo_col = cursor.getColumnIndexOrThrow(DBConstants.MEDIA_OBJECT_PHOTO);
            view_cache.video_col = cursor.getColumnIndexOrThrow(DBConstants.MEDIA_OBJECT_VIDEO);
            view_cache.investigation_col = cursor.getColumnIndexOrThrow(DBConstants.MEDIA_OBJECT_INVESTIGATION);
            view_cache.mission_col = cursor.getColumnIndexOrThrow(DBConstants.MEDIA_OBJECT_MISSION);
            view_cache.user_thumbnail_col = cursor.getColumnIndexOrThrow(DBConstants.MEDIA_OBJECT_USER_THUMBNAIL);
            view_cache.last_edited_col = cursor.getColumnIndexOrThrow(DBConstants.LAST_EDITED);
            view_cache.location_col = cursor.getColumnIndexOrThrow(DBConstants.MEDIA_OBJECT_METRO_CODE);
            view_cache._id_col = cursor.getColumnIndexOrThrow(DBConstants.ID);
            view.setTag(R.id.list_item_cache, view_cache);
            
        }

        if(view_cache.progressBar != null)
            view_cache.progressBar.setVisibility(View.GONE);

        if(cursor.getString(view_cache.title_col) == null || cursor.getString(view_cache.title_col).compareTo("")==0)
            view_cache.title.setVisibility(View.GONE);
        else{
            view_cache.title.setText(cursor.getString(view_cache.title_col));
            Linkify.addLinks(view_cache.title, pattern, scheme, null, mentionFilter);
            view_cache.title.setVisibility(View.VISIBLE);
            view_cache.title.setMovementMethod(null); // We're using a custom TextView to only intercept touches on links
        }
        view_cache.username.setText(cursor.getString(view_cache.username_col));
        if(cursor.getString(view_cache.user_thumbnail_col) != null && cursor.getString(view_cache.user_thumbnail_col).compareTo("") != 0){
            ImageLoader.getInstance().displayImage(cursor.getString(view_cache.user_thumbnail_col), view_cache.userThumbnail);
        }else{
            view_cache.userThumbnail.setImageResource(R.drawable.user_placeholder);
        }

        view_cache.thumbnail.bringToFront();
        view_cache.playButton.bringToFront();

        if(cursor.getString(view_cache.location_col) != null && cursor.getString(view_cache.location_col).compareTo("") != 0){
            view_cache.location.setVisibility(View.VISIBLE);
            view_cache.location.setText(cursor.getString(view_cache.location_col));
        }else{
            view_cache.location.setVisibility(View.INVISIBLE);
        }
        //view_cache.views.setText(cursor.getString(view_cache.views_col));
        //view_cache.actions.setText(cursor.getString(view_cache.actions_col));
        
        if(view_cache.last_seen_id != cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.ID)) && cursor.getString(view_cache.thumbnail_col) != null && cursor.getString(view_cache.thumbnail_col).compareTo("") != 0){
        	ImageLoader.getInstance().displayImage(cursor.getString(view_cache.thumbnail_col), view_cache.thumbnail);
        }else if(cursor.getString(view_cache.thumbnail_col) == null || cursor.getString(view_cache.thumbnail_col).compareTo("") == 0){
        	view_cache.thumbnail.setImageResource(R.drawable.thumbnail_placeholder);
        }

        view_cache.iconContainer.setVisibility(View.GONE);

        if(!cursor.isNull(view_cache.audio_col)){
            //view_cache.typeIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.microphone_icon));
            view_cache.playButton.setVisibility(View.GONE);
            view_cache.userThumbnail.setVisibility(View.VISIBLE);
            view_cache.username.setVisibility(View.VISIBLE);
        }else if(!cursor.isNull(view_cache.investigation_col)){
            //view_cache.typeIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.investigation_icon));
            view_cache.playButton.setVisibility(View.GONE);
            view_cache.userThumbnail.setVisibility(View.VISIBLE);
            view_cache.username.setVisibility(View.VISIBLE);
        }else if(!cursor.isNull(view_cache.photo_col)){
            //view_cache.typeIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.camera_icon));
            view_cache.playButton.setVisibility(View.GONE);
            view_cache.userThumbnail.setVisibility(View.VISIBLE);
            view_cache.username.setVisibility(View.VISIBLE);
        }else if(!cursor.isNull(view_cache.video_col)){
            //view_cache.typeIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.camcorder_icon));
            view_cache.playButton.setVisibility(View.VISIBLE);
            view_cache.userThumbnail.setVisibility(View.VISIBLE);
            view_cache.username.setVisibility(View.VISIBLE);
        }else if(!cursor.isNull(view_cache.mission_col)){
            //view_cache.typeIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.mission_icon));
            view_cache.playButton.setVisibility(View.GONE);
            //view_cache.userThumbnail.setVisibility(View.GONE);
            //view_cache.username.setVisibility(View.GONE);
            view_cache.iconContainer.setVisibility(View.VISIBLE);
            view_cache.iconContainer.bringToFront();
            view_cache.members.bringToFront();
            view_cache.submissions.bringToFront();
            if(cursor.getString(view_cache.members_col) != null && cursor.getString(view_cache.members_col).length() > 0){
                view_cache.members.setText(cursor.getString(view_cache.members_col));
            }else{
                view_cache.members.setText("0");
            }

            if(cursor.getString(view_cache.submissions_col) != null && cursor.getString(view_cache.submissions_col).length() > 0){
                view_cache.submissions.setText(cursor.getString(view_cache.submissions_col));
            }else{
                view_cache.submissions.setText("0");
            }
            // and report expires time for mission, not last edited
        }
        try{
            view_cache.lastEdited.setVisibility(View.VISIBLE);
            if(cursor.isNull(view_cache.mission_col) && !cursor.isNull(view_cache.last_edited_col))
                view_cache.lastEdited.setText(DateUtils.getRelativeTimeSpanString(Constants.utc_formatter.parse(cursor.getString(view_cache.last_edited_col)).getTime()) );
            else if(!cursor.isNull(view_cache.mission_col) && !cursor.isNull(view_cache.expires_col)){
                view_cache.lastEdited.setText("Expires " + DateUtils.getRelativeTimeSpanString(Constants.utc_formatter.parse(cursor.getString(view_cache.expires_col)).getTime()) );
            }else{
                view_cache.lastEdited.setVisibility(View.INVISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        view.setTag(R.id.list_item_model, cursor.getInt(view_cache._id_col));
	
        view_cache.last_seen_id = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.ID));
	}
	
	// Cache the views within a ListView row item 
    static class ViewCache {
        TextView title;
        TextView username;
        ImageView thumbnail;
        TextView lastEdited;
        TextView location;
        //ImageView typeIcon;
        ImageView playButton;
        ImageView userThumbnail;
        ProgressBar progressBar;
        TextView members;
        TextView submissions;
        LinearLayout iconContainer;
        
        int last_seen_id;
                
        int title_col; 
        int thumbnail_col;
        int username_col;
        int user_thumbnail_col;
        int last_edited_col;
        int location_col;
        int members_col;
        int submissions_col;
        int expires_col;
        int _id_col;

        int audio_col;
        int photo_col;
        int video_col;
        int investigation_col;
        int mission_col;
    }

}
