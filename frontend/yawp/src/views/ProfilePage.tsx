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

function ProfilePage(props:any) {

    let [user, setUser] = React.useState<any>({});

    const username = props.match.params.username 

    React.useEffect(() => {
        getUser();
    }, [user.userId])

    const getUser = async () => {
        let res = await axios.get(`http://localhost:9025/users/username/${username}`);
        setUser(res.data);
        getYawps(res.data.userId);
    }

    const state = useSelector<any, any>((state) => state);

    const dispatch = useDispatch();

    /*
    const getUser = () => {
        dispatch(
            fetchUser(username)
        )
    };
    */
    const getYawps = (userId:number) => {
        dispatch(
            fetchUserYawps(userId)
        )
    }

    return(
        <div>
            <Navbar />
            <div className="profile-page">
                <div className="profile-container">
                    <ProfileHeader username={username} bio={user.bio}></ProfileHeader>
                    {state.yawp.items.map((item: any) => {
                            return (
                                <YawpPost id={item.yawpId} username={item.authorUsername} content={item.message} profilePic={item.authorPic} likes={item.likes.length} key={item.yawpId} />
                            )
                        })}
                </div>
            </div>
        </div>

    )
}

export default ProfilePage;