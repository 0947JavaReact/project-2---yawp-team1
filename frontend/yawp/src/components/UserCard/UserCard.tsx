import React, { useState } from 'react';
import {Link, useHistory} from 'react-router-dom';

export interface User {
    
    username: string,
    profilePic: string,
    bio: string
};

export const UserCard: React.FC<User> = (props) => {
    

    return (
        <div className="user-card">
            <div className="card-profile">
            <Link to={`/user/${props.username}`}> <img className="card-image" src={props.profilePic} /></Link>
                <h5 className="card-username">{props.username}</h5>
            </div>
            <div>
                <p>{props.bio}</p>
                {props.username === localStorage.getItem("username") ? <></> : <button>Follow</button>}
            </div>
        </div>
    )
}

export default UserCard;