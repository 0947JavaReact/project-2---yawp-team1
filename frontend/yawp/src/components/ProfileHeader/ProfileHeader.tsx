import * as React from 'react';
import img from '../../img/profile-picture-default.jpeg';
import {Link} from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';


export const ProfileHeader: React.FC<any> = (props) => {

    const state = useSelector<any, any>((state) => state);

    return(
        <div className="profile">
            <div>
                <div>
                <img src={img}></img>
                <h4>Username</h4>
                </div>
                {state.user.user.username === props.username ? <button >Edit Profile</button> : <button >Follow</button>}
            </div>
            <div>
                <h4>Bio</h4>
            </div>
            <div>
                <Link to={`/followers/${props.username}`}>Followers</Link>
                <Link to={`/following/${props.username}`}>Following</Link>
            </div>
        </div>
    )
}