import React, { useState } from 'react';
import {Link, useHistory} from 'react-router-dom';
import './UserCard.css';

export interface User {
    
    username: string,
    profilePic: string,
    bio: string
};

export const UserCard: React.FC<User> = (props) => {
    

    return (
        <div className="user-card">
            <div className="user-card-container">
                <div className="user-card-info">
                    <Link to={`/user/${props.username}`}><img className="user-card-image" src={props.profilePic} /></Link>
                     <h4 className="user-card-bio">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas et vehicula tortor, volutpat tristique orci. Quisque auctor porta tristique. Aliquam purus nibh, dictum ac malesuada faucibus, convallis interdum leo. Sed porttitor laoreet viverra. Integer eu tincidunt mi. Morbi in urna enim. Cras sit amet mattis justo, ac vestibulum ipsum.</h4>
                </div>
                <div className="card-name-button">
                    <h3>{props.username}</h3>
                    {props.username === localStorage.getItem("username") ? <></> : <button className="user-card-button">Follow</button>}
                </div>
            </div>
        </div>
    )
}

export default UserCard;