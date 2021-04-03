import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import axios from 'axios';
import {updateFollowingList} from '../../actions/userActions';
import './UserCard.css';

export const UserCard: React.FC<any> = (props) => {
    const dispatch = useDispatch();
    const state = useSelector<any, any>((state) => state);

    const followUser = async () => {
        
        let res = await axios.post(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/startfollowing`, {
            user_id: state.user.user.id,
            following_id: props.id
        });
        
        dispatch(
            updateFollowingList(props.id)
        );
    };

    useEffect(()=> {
        getFollowButton();
        console.log(state.user.user.loggedInFollowing);
        console.log(props.id);
    }, [props.username, state.user.user.loggedInFollowing]);

    const getFollowButton = () => {
        if(props.username === state.user.user.username){
            return (
                <></>
            );
        }
        else if(state.user.user.loggedInFollowing.includes(props.id)){
            return (
                <></>
            );
        }
        else{
            return (<button className="user-card-button" onClick={followUser}>Follow</button>);
        }
    }

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
                {getFollowButton()}
            </div>
        </div>
    )
}

export default UserCard;