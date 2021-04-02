import * as React from 'react';
import {Link} from 'react-router-dom';
import {ProfileHeader} from '../components/ProfileHeader/ProfileHeader';
import { useDispatch, useSelector } from 'react-redux';
//import { fetchUser } from '../actions/userActions';
import { clearYawps, fetchUserYawps } from '../actions/yawpActions';
import YawpPost from '../components/YawpPost/YawpPost';
import './ProfilePage.css';
import Navbar from '../components/Navbar/Navbar';
import axios from 'axios';
import logo from '../img/YAWP_logo.gif';

function ProfilePage(props:any) {
    let [user, setUser] = React.useState<any>({});
    let [loggedInFollowers, setLoggedInFollowers] = React.useState<any>([]);
    let [loading, setLoading] = React.useState(true);
    const username = props.match.params.username 
    const state = useSelector<any, any>((state) => state);
    React.useEffect(() => {
        setLoading(true);
        deleteYawps();
        getUser();
        getLoggedInFollowers();
        console.log("In the profile page: " + JSON.stringify(state.user.user));
    }, [user.userId, username, user.bio, user.profilePic, state.yawps]);

    const getUser = async () => {
        let res = await axios.get(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/username/${username}`);
        user = {
            username: res.data.username,
            id: res.data.userId,
            bio: res.data.bio,
            email: res.data.email,
            profilePic: res.data.picUrl
        }
        setUser(user);
        console.log("in the profile page: " + JSON.stringify(user));
        getYawps(res.data.userId);
    }

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

    const dispatch = useDispatch();

    const deleteYawps = async () => {
        dispatch(clearYawps);
    }

    const getYawps = async (userId:number) => {
        await dispatch(
            fetchUserYawps(userId)
        )
        setLoading(false);
    }

    return(
        <div>
            {loading ? <div className="profile-loading"><img src={logo} height={500} width={500}/></div> : 
            (<div>
                <Navbar />
                <div className="profile-page">
                    <div className="profile-container">
                        <ProfileHeader userId={user.id} username={username} bio={user.bio} profilePic={user.profilePic} showFollowButton={loggedInFollowers.includes(user.userId)}/>
                        {state.yawp.items.map((item: any) => {
                                return (
                                    <YawpPost yawp={item} key={item.yawpId} />
                                )
                            })}
                    </div>
                </div>
            </div>)}
        </div>
    )
}

export default ProfilePage;