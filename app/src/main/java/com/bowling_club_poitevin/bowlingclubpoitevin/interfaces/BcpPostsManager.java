package com.bowling_club_poitevin.bowlingclubpoitevin.interfaces;


import com.bowling_club_poitevin.bowlingclubpoitevin.utils.website.BcpPost;

public interface BcpPostsManager {

    void downloadBcpPostsList();

    void load(BcpPost post);
}
