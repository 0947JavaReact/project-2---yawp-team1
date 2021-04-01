import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import axios from 'axios';
import './UserCard.css';

export interface User {
    id: number,
    username: string,
    profilePic: string,
    bio: string,
    showFollowButton: boolean
};

export const UserCard: React.FC<User> = (props) => {
    let [buttonRemoval, setButtonRemoval] = useState(props.showFollowButton);
    const state = useSelector<any, any>((state) => state);
    const followUser = async () => {
        let res = await axios.post(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/startfollowing`, {
            user_id: state.user.user.id,
            following_id: props.id
        });

        setButtonRemoval(true);
    };

    return (
        <div className="user-card">
            <div className="user-card-container">
                <div className="user-card-info">
                    <Link to={`/user/${props.username}`}><img className="user-card-image" src={props.profilePic} /></Link>
                    <h3>{props.username}</h3>
                </div>
                <div className="card-name-button">
                    <h4 className="user-card-bio">{props.bio}</h4>
                </div>
                {props.username === state.user.user.username || (props.showFollowButton || buttonRemoval) ? <></> : <button className="user-card-button" onClick={followUser}>Follow</button>}
            </div>
        </div>
    )
}

export default UserCard;