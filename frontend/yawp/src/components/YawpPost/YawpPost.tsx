import React, { useState } from 'react';
import './YawpPost.css';
import ThumbUpIcon from '@material-ui/icons/ThumbUp';

export interface Yawp {
    id: number,
    content: string,
    username: string,
    profilePic: string
    likes: number
};

export const YawpPost: React.FC<Yawp> = (props) => {
    return (
        <div className="yawp-container">
            <div className="yawp-profile">
                <img className="yawp-image" src={props.profilePic} />
                <h3 className="yawp-username">{props.username}</h3>
            </div>

            <div className="yawp-content">
                <p>{props.content}</p>
            </div>

            <div className="yawp-likes">
                <button className="yawp-like-button"><ThumbUpIcon style={{fontSize:30}}/></button>
                <p className="likes">{props.likes}</p>
            </div>
        </div>
    )
}

export default YawpPost;
