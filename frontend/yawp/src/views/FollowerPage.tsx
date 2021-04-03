import * as React from 'react';
import { useSelector } from 'react-redux';
import { Navbar } from '../components/Navbar/Navbar';
import UserCard from '../components/UserCard/UserCard';
import axios from 'axios';
import './FollowerPage.css';
import logo from '../img/YAWP_logo.gif';

function FollowerPage(props: any) {
    let [followers, setFollowers] = React.useState<any>([])
    let [user, setUser] = React.useState<any>({});
    let [loading, setLoading] = React.useState(true);
    const state = useSelector<any, any>((state) => state);
    const username = props.match.params.username;

    React.useEffect(() => {
        getUser();

        if(user.userId)
        {
            console.log(user.userId);
            getFollowers(user.userId);
        }
    }, [user.userId, followers.length]);

    const getUser = async () => {
        let res = await axios.get(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/username/${username}`);
        setUser(res.data);
    }

    const getFollowers = async (userId: number) => {
       
       
        try{
            let res = await axios.post('http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/followers', {
            user_id: userId
            });

        setFollowers(res.data);
        }
        catch(e)
        {
            setFollowers([]);
        }

        setLoading(false);

    }

    return (
        <div> {loading ? <div className="following-loading"><img src={logo} height={500} width={500}/></div> : (
        <div>
            <Navbar />
            <div className="follower-page">
                <div className="follower-container">
                    <h1 className="follower-h1">{`${username}'s followers`}</h1>
                    {followers.map((user: any) => {
                        return <UserCard id={user.userId} username={user.username} bio={user.bio} profilePic={user.picUrl}></UserCard>
                    })}
                </div>
            </div>
        </div>)}
        </div>
    )
}

export default FollowerPage