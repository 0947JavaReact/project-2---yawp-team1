import * as React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Navbar } from '../components/Navbar/Navbar';
import img1 from '../img/profile-picture-default.jpeg';
import UserCard from '../components/UserCard/UserCard';
import axios from 'axios';
import './FollowerPage.css';

function FollowerPage(props:any) {

    let [followers, setFollowers] = React.useState<any>([]) 
    let [user, setUser] = React.useState<any>({});

    const username = props.match.params.username 

    React.useEffect(() => {
        getUser();
        
    }, [user.userId]);

    const getUser = async () => {
        let res = await axios.get(`http://localhost:9025/users/username/${username}`);
        setUser(res.data);
        getFollowers(user.userId);
    }

    const getFollowers = async (userId:number) => {

        let res = await axios.post('http://localhost:9025/users/followers', {user_id: user.userId});
        setFollowers(res.data);

    }

    return (
        <div>
            <Navbar />
            <div className="follower-page">
                <div className="follower-container">
                    <h1 className="follower-h1">{`${username}'s followers`}</h1>
                    {followers.map((user:any) => {
                        return <UserCard username={user.username} bio={user.bio} profilePic={user.profilePic}></UserCard>
                    })}
                </div>
            </div>
        </div>
    )
}

export default FollowerPage