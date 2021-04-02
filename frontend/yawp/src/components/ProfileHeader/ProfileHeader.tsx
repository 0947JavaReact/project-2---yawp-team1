import * as React from 'react';
import img from '../../img/profile-picture-default.jpeg';
import { Link, useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import axios from 'axios';
import './ProfileHeader.css';

export const ProfileHeader: React.FC<any> = (props) => {
    let [buttonRemoval, setButtonRemoval] = React.useState(props.showFollowButton);
    let [showEdit, setShowEdit] = React.useState(false);
    let [loggedInFollowers, setLoggedInFollowers] = React.useState<any>([]);
    const state = useSelector<any, any>((state) => state);
    const history = useHistory();

    React.useEffect(() => {
       
        getLoggedInFollowers();
        console.log(loggedInFollowers);
    }, [loggedInFollowers.length, state.user.user, state.user.user.profilePic, state.user.user.bio]);


    const goToEditProfilePage = () => {
        history.push('/edit');
    };
    
    const getLoggedInFollowers = async () => {
        let res = await axios.post('http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/following', {
            user_id: state.user.user.id
        });

        let loggedInFollowerIds = [];
        for (let i = 0; i < res.data.length; i++) {
            loggedInFollowerIds.push(res.data[i].userId);
        }

        setLoggedInFollowers(loggedInFollowerIds);
    }

    const followUser = async() => {
        console.log(props.userId);
        let res = await axios.post(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/startfollowing`, {
            user_id: state.user.user.id,
            following_id: props.userId
        });
        setLoggedInFollowers([...loggedInFollowers, props.userId]);
    } 

    const pushToFollowers = () => {
        history.push(`/followers/${props.username}`);
    }

    const pushToFollowing = () => {
        history.push(`/following/${props.username}`);
    }

    return (
        <div className="header">
            <div className="header-container">
                <div className="header-info">
                    <img className="header-pic" src={props.profilePic} alt="profile-pic"></img>
                    <h1>{props.username}</h1>
                </div>
                <div className="header-name-button">
                    <h4 className="header-bio">{props.bio}</h4>
                </div>
                {state.user.user.username === props.username ? <button className="header-button" onClick={goToEditProfilePage}>Edit Profile</button> : (!loggedInFollowers.includes(props.userId) ? <button onClick={followUser}className="header-button">Follow</button> : <></>)}
            </div>
            <div className="following-followers">
                <button className="header-button" onClick={pushToFollowers}>Followers</button>
                <button className="header-button" onClick={pushToFollowing}>Following</button>
            </div>
        </div>
    )
}