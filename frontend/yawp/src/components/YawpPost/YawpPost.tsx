import React, { useState } from 'react';
import {Link} from 'react-router-dom';
import './YawpPost.css';
import ThumbUpIcon from '@material-ui/icons/ThumbUp';
import { useDispatch, useSelector } from 'react-redux';
import {likeYawp} from '../../actions/yawpActions'


export const YawpPost: React.FC<any> = (props) => {

    const dispatch = useDispatch();
    const state = useSelector<any, any>((state) => state);

    function likeThatYawp() {

        let ids = []

        for (let i = 0; i < props.yawp.likes.length; i++) {

            ids.push(parseInt(props.yawp.likes[i].userId));
        }

        if(!ids.includes(parseInt(state.user.user.id)))
        {
           dispatch(
            likeYawp({yawp:props.yawp, userId: state.user.user.id })
        );
        }
    }

    return (
        <div className="yawp-container">
            <div className="yawp-profile">
                <Link to={`/user/${props.yawp.authorUsername}`}><img className="yawp-image" src={props.yawp.authorPic} /></Link>
                <h3 className="yawp-username">{props.yawp.authorUsername}</h3>
            </div>

            <div className="yawp-content">
                <p>{props.yawp.message}</p>
            </div>

            <div className="yawp-likes">
                <button className="yawp-like-button" onClick= {likeThatYawp}><ThumbUpIcon style={{fontSize:30}}/></button>
                <p className="likes">{props.yawp.likes.length}</p>
            </div>
        </div>
    )
}

export default YawpPost;
