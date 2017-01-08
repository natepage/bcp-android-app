package com.bowling_club_poitevin.bowlingclubpoitevin.interfaces;

import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpPost;

public interface BcpPostsListViewHolder extends PackageNameConsumer {
    void bind(BcpPost post);

    void setPostsManager(BcpPostsManager manager);
}
