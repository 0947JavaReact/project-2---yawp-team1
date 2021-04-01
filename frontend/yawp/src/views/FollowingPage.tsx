import * as React from 'react';
import { useSelector } from 'react-redux';
import { Navbar } from '../components/Navbar/Navbar';
import UserCard from '../components/UserCard/UserCard';
import axios from 'axios';
import './FollowingPage.css';
import logo from '../img/YAWP_logo.gif';

function FollowingPage(props:any) {
    let [following, setFollowing] = React.useState<any>([]);
    let [loggedInFollowers, setLoggedInFollowers] = React.useState<any>([]);
    let [user, setUser] = React.useState<any>({});
    let [loading, setLoading] = React.useState(true);
    const state = useSelector<any, any>((state) => state);
    const username = props.match.params.username 

    React.useEffect(() => {
        getUser();

        if(user.userId)
        {
            getFollowing(user.userId);
        }
        
    }, [user.userId, loggedInFollowers.length, following.length]);

    const getUser = async () => {
        let res = await axios.get(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/username/${username}`);
        setUser(res.data);
        getLoggedInFollowers();
    }

    const getFollowing = async (userId:number) => {
        try{
        let res = await axios.post('http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/following', {
            user_id: user.userId
        });
        setFollowing(res.data);
        }catch(e){
            setFollowing([]);
        }
        
        setLoading(false);
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

    return (
        <div> {loading ? <div className="following-loading"><img src={logo} height={500} width={500}/></div> :(
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
        </div>)}
        </div>
    )
}

export default FollowingPage