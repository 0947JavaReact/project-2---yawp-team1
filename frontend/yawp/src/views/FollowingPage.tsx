import * as React from 'react';
import { Navbar } from '../components/Navbar/Navbar';
import img1 from '../img/profile-picture-default.jpeg';
import UserCard from '../components/UserCard/UserCard';
import axios from 'axios';
import './FollowingPage.css';

function FollowingPage(props:any) {

    let [following, setFollowing] = React.useState<any>([]) 
    let [user, setUser] = React.useState<any>({});

    const username = props.match.params.username 

    React.useEffect(() => {
        getUser();
    }, [user.userId]);

    const getUser = async () => {
        let res = await axios.get(`http://localhost:9025/users/username/${username}`);
        setUser(res.data);
        getFollowing(user.userId);
    }

    const getFollowing = async (userId:number) => {

        let res = await axios.post('http://localhost:9025/users/following', {user_id: user.userId});
        setFollowing(res.data);

    }

    return (
        <div>
            <Navbar />
        <div className="following-page">
            <div className="following-container">
            <h1 className="following-h1">{`Who ${username} is following`}</h1>
            {following.map((user:any) => {
                
                return <UserCard username={user.username} bio={user.bio} profilePic={user.profilePic}></UserCard>
            })}
            </div>
        </div>
        </div>
    )
}

export default FollowingPage