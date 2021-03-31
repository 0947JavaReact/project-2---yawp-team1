import * as React from 'react';
import img from '../../img/profile-picture-default.jpeg';
import { Link, useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import axios from 'axios';
import './ProfileHeader.css';

export const ProfileHeader: React.FC<any> = (props) => {
    let [loggedInFollowers, setLoggedInFollowers] = React.useState<any>([]);
    const state = useSelector<any, any>((state) => state);
    const history = useHistory();

    React.useEffect(() => {
        getLoggedInFollowers();
        console.log(loggedInFollowers);
    }, [loggedInFollowers.length]);


    const goToEditProfilePage = () => {
        history.push("/edit");
    };
    
    const getLoggedInFollowers = async () => {
        let res = await axios.post('http://localhost:9025/users/following', {
            user_id: state.user.user.id
        });

        let loggedInFollowerIds = [];
        for (let i = 0; i < res.data.length; i++) {
            loggedInFollowerIds.push(res.data[i].userId);
        }

        setLoggedInFollowers(loggedInFollowerIds);
    }

    return (
        <div className="header">
            <div className="header-container">
                <div className="header-info">
                    <img className="header-pic" src={props.profilePic} alt="profile-pic"></img>
                    <h4 className="header-bio">{props.bio}</h4>
                </div>
                <div className="header-name-button">
                    <h1>{props.username}</h1>
                    {state.user.user.username === props.username ? <button className="header-button" onClick={goToEditProfilePage}>Edit Profile</button> : (!loggedInFollowers.includes(props.userId) ? <button className="header-button">Follow</button> : <></>)}
                </div>
                <div className="following-followers">
                    <Link className="profile-link" to={`/followers/${props.username}`}>Followers</Link>
                    <Link className="profile-link" to={`/following/${props.username}`}>Following</Link>
                </div>
            </div>
        </div>
    )
}