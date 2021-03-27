import * as React from 'react';
import img from '../../img/profile-picture-default.jpeg';
import {Link} from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import './ProfileHeader.css';

export const ProfileHeader: React.FC<any> = (props) => {

    const state = useSelector<any, any>((state) => state);

    return(
        <div className="header">
            <div className="header-container">
                <div className="header-info">
                    <img className="header-pic" src={img}></img>
                     <h4 className="header-bio">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas et vehicula tortor, volutpat tristique orci. Quisque auctor porta tristique. Aliquam purus nibh, dictum ac malesuada faucibus, convallis interdum leo. Sed porttitor laoreet viverra. Integer eu tincidunt mi. Morbi in urna enim. Cras sit amet mattis justo, ac vestibulum ipsum.</h4>
                </div>
                <div className="header-name-button">
                    <h1>{props.username}</h1>
                    {state.user.user.username === props.username ? <button className="header-button">Edit Profile</button> : <button className="header-button">Follow</button>}
                </div>
                <div className="following-followers">
                    <Link className="profile-link" to={`/followers/${props.username}`}>Followers</Link>
                    <Link className="profile-link" to={`/following/${props.username}`}>Following</Link>
                </div>
            </div>
        </div>
    )
}