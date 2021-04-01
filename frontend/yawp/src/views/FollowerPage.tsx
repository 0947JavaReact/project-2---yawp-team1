import * as React from 'react';
import { useSelector } from 'react-redux';
import { Navbar } from '../components/Navbar/Navbar';
import UserCard from '../components/UserCard/UserCard';
import axios from 'axios';
import './FollowerPage.css';
import logo from '../img/YAWP_logo.gif';

function FollowerPage(props: any) {
    let [followers, setFollowers] = React.useState<any>([])
    let [loggedInFollowers, setLoggedInFollowers] = React.useState<any>([]);
    let [user, setUser] = React.useState<any>({});
    let [loading, setLoading] = React.useState(true);
    const state = useSelector<any, any>((state) => state);
    const username = props.match.params.username;

    React.useEffect(() => {
        getUser();

        if(user.userId)
        {
            getFollowers(user.userId);
        }
    }, [user.userId, loggedInFollowers.length, followers.length]);

    const getUser = async () => {
        let res = await axios.get(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/username/${username}`);
        setUser(res.data);
        getLoggedInFollowers();
    }

    const getFollowers = async (userId: number) => {
       
       
        try{
            let res = await axios.post('http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/followers', {
            user_id: user.userId

        });

        setFollowers(res.data);
        }
        catch(e)
        {
            setFollowers([])
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
        <div> {loading ? <div className="following-loading"><img src={logo} height={500} width={500}/></div> : (
        <div>
            <Navbar />
            <div className="follower-page">
                <div className="follower-container">
                    <h1 className="follower-h1">{`${username}'s followers`}</h1>
                    {followers.map((user: any) => {
                        return <UserCard id={user.userId} username={user.username} bio={user.bio} profilePic={user.picUrl} showFollowButton={loggedInFollowers.includes(user.userId)}></UserCard>
                    })}
                </div>
            </div>
        </div>)}
        </div>
    )
}

export default FollowerPage