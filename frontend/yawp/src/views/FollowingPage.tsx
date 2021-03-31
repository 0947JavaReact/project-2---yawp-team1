import * as React from 'react';
import { useSelector } from 'react-redux';
import { Navbar } from '../components/Navbar/Navbar';
import UserCard from '../components/UserCard/UserCard';
import axios from 'axios';
import './FollowingPage.css';

function FollowingPage(props:any) {
    let [following, setFollowing] = React.useState<any>([]);
    let [loggedInFollowers, setLoggedInFollowers] = React.useState<any>([]);
    let [user, setUser] = React.useState<any>({});
    const state = useSelector<any, any>((state) => state);
    const username = props.match.params.username 

    React.useEffect(() => {
        getUser();
        getLoggedInFollowers();
    }, [user.userId, loggedInFollowers.length, following.length]);

    const getUser = async () => {
        let res = await axios.get(`http://localhost:9025/users/username/${username}`);
        setUser(res.data);
        getFollowing(user.userId);
    }

    const getFollowing = async (userId:number) => {
        let res = await axios.post('http://localhost:9025/users/following', {
            user_id: user.userId
        });

        setFollowing(res.data);
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

    return (
        <div>
            <Navbar />
        <div className="following-page">
            <div className="following-container">
            <h1 className="following-h1">{`Who ${username} is following`}</h1>
            {following.map((user:any) => {
                return <UserCard id={user.userId} username={user.username} bio={user.bio} profilePic={user.picUrl} showFollowButton={loggedInFollowers.includes(user.userId)} />
            })}
            </div>
        </div>
        </div>
    )
}

export default FollowingPage