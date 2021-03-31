import * as React from 'react';
import img from '../../img/profile-picture-default.jpeg';
import { Link, useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import './ProfileHeader.css';

export const ProfileHeader: React.FC<any> = (props) => {
    const state = useSelector<any, any>((state) => state);
    const history = useHistory()

    const goToEditProfilePage = () => {
        history.push("/edit");
    };
    
    console.log(state);

    return (
        <div className="header">
            <div className="header-container">
                <div className="header-info">
                    <img className="header-pic" src={state.user.user.profilePic} alt="profile-pic"></img>
                    <h4 className="header-bio">{props.bio}</h4>
                </div>
                <div className="header-name-button">
                    <h1>{props.username}</h1>
                    {state.user.user.username === props.username ? <button className="header-button" onClick={goToEditProfilePage}>Edit Profile</button> : <button className="header-button">Follow</button>}
                </div>
                <div className="following-followers">
                    <Link className="profile-link" to={`/followers/${props.username}`}>Followers</Link>
                    <Link className="profile-link" to={`/following/${props.username}`}>Following</Link>
                </div>
            </div>
        </div>
    )
}