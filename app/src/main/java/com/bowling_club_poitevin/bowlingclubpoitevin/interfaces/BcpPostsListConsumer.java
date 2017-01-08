package com.bowling_club_poitevin.bowlingclubpoitevin.interfaces;

import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpPost;
import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpPostsList;

import java.util.List;

public interface BcpPostsListConsumer extends PackageNameConsumer {
    void setPostsList(BcpPostsList bcpPostsList);
}
