import * as React from 'react';
import {Link} from 'react-router-dom';
import {ProfileHeader} from '../components/ProfileHeader/ProfileHeader';
import { useDispatch, useSelector } from 'react-redux';
//import { fetchUser } from '../actions/userActions';
import { fetchUserYawps } from '../actions/yawpActions';
import YawpPost from '../components/YawpPost/YawpPost';
import './ProfilePage.css';
import Navbar from '../components/Navbar/Navbar';
import axios from 'axios';
import CircularProgress from "@material-ui/core/CircularProgress";

function ProfilePage(props:any) {
    let [user, setUser] = React.useState<any>({});
    let [loggedInFollowers, setLoggedInFollowers] = React.useState<any>([]);
    let [loading, setLoading] = React.useState(true);
    const username = props.match.params.username 

    React.useEffect(() => {
        setLoading(true);
        getUser();
        getLoggedInFollowers();
        console.log("In the profile page: " + JSON.stringify(state.user.user));
    }, [user.userId, username, user.bio, user.profilePic]);

    const getUser = async () => {
        let res = await axios.get(`http://localhost:9025/users/username/${username}`);
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
        let res = await axios.post('http://localhost:9025/users/following', {
            user_id: state.user.user.id
        });

        let loggedInFollowerIds = [];
        for (let i = 0; i < res.data.length; i++) {
            loggedInFollowerIds.push(res.data[i].userId);
        }

        setLoggedInFollowers(loggedInFollowerIds);
    }

    const state = useSelector<any, any>((state) => state);
    const dispatch = useDispatch();

    const getYawps = async (userId:number) => {
        await dispatch(
            fetchUserYawps(userId)
        )
        setLoading(false);
    }

    return(
        <div>
            {loading ? <div className="profile-loading"><CircularProgress style={{width:80, height: 80, textAlign:'center', color: 'black'}}/></div> : 
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