package org.yeewoe.listener.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import org.yeewoe.listener.R;
import org.yeewoe.listener.mvp.model.Album;
import org.yeewoe.listener.util.ATEUtil;
import org.yeewoe.listener.util.ListenerUtil;
import org.yeewoe.listener.util.NavigationUtil;

/**
 * Created by yeewoe on 2016/11/24.
 */

public class ArtistAlbumAdapter extends RecyclerView.Adapter<ArtistAlbumAdapter.ItemHolder>{

    private List<Album> arraylist;
    private Activity mContext;

    public ArtistAlbumAdapter(Activity context, List<Album> arraylist) {
        this.arraylist = arraylist;
        this.mContext = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_artist_album, viewGroup, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemHolder itemHolder, int i) {

        Album localItem = arraylist.get(i);

        itemHolder.title.setText(localItem.title);
        String songCount = ListenerUtil.makeLabel(mContext, R.plurals.Nsongs, localItem.songCount);
        itemHolder.details.setText(songCount);

        Glide.with(mContext).load(ListenerUtil.getAlbumArtUri(localItem.id).toString())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(ATEUtil.getDefaultAlbumDrawable(mContext))
                .centerCrop()
                .into(itemHolder.albumArt);

        if (ListenerUtil.isLollipop())
            itemHolder.albumArt.setTransitionName("transition_album_art" + i);
    }

    @Override
    public int getItemCount() {
        return (null != arraylist ? arraylist.size() : 0);
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView details;
        ImageView albumArt;
        CardView rootView;

        public ItemHolder(View view) {
            super(view);
            this.rootView = (CardView) view.findViewById(R.id.root_view);
            this.title = (TextView) view.findViewById(R.id.album_title);
            this.details = (TextView) view.findViewById(R.id.album_details);
            this.albumArt = (ImageView) view.findViewById(R.id.album_art);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            NavigationUtil.navigateToAlbum(mContext, arraylist.get(getAdapterPosition()).id,
                    arraylist.get(getAdapterPosition()).title,
                    new Pair<View, String>(albumArt, "transition_album_art" + getAdapterPosition()));
        }

    }
}
