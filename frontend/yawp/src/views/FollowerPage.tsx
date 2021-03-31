import * as React from 'react';
import { useSelector } from 'react-redux';
import { Navbar } from '../components/Navbar/Navbar';
import UserCard from '../components/UserCard/UserCard';
import axios from 'axios';
import './FollowerPage.css';

function FollowerPage(props: any) {
    let [followers, setFollowers] = React.useState<any>([])
    let [loggedInFollowers, setLoggedInFollowers] = React.useState<any>([]);
    let [user, setUser] = React.useState<any>({});
    const state = useSelector<any, any>((state) => state);
    const username = props.match.params.username;

    React.useEffect(() => {
        getUser();
        getLoggedInFollowers();
    }, [user.userId, loggedInFollowers.length]);

    const getUser = async () => {
        let res = await axios.get(`http://localhost:9025/users/username/${username}`);
        setUser(res.data);
        getFollowers(user.userId);
    }

    const getFollowers = async (userId: number) => {
        let res = await axios.post('http://localhost:9025/users/followers', {
            user_id: user.userId
        });

        setFollowers(res.data);
    }

    const getLoggedInFollowers = async () => {
        let res = await axios.post('http://localhost:9025/users/following', {
            user_id: state.user.user.id
        });

        setLoggedInFollowers(res.data);
    }

    return (
        <div>
            <Navbar />
            <div className="follower-page">
                <div className="follower-container">
                    <h1 className="follower-h1">{`${username}'s followers`}</h1>
                    {followers.map((user: any) => {
                        return <UserCard id={user.userId} username={user.username} bio={user.bio} profilePic={user.profilePic} showFollowButton={loggedInFollowers.includes(user)}></UserCard>
                    })}
                </div>
            </div>
        </div>
    )
}

export default FollowerPage