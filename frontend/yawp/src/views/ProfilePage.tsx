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
    }, [user.userId, username])

    const getUser = async () => {
        let res = await axios.get(`http://localhost:9025/users/username/${username}`);
        setUser(res.data);
        getYawps(res.data.userId);
    }

    const state = useSelector<any, any>((state) => state);
    const dispatch = useDispatch();

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
                    <ProfileHeader userId={user.userId} username={username} bio={user.bio} profilePic={user.picUrl} />
                    {state.yawp.items.map((item: any) => {
                            return (
                                <YawpPost yawp={item} key={item.yawpId} />
                            )
                        })}
                </div>
            </div>
        </div>

    )
}

export default ProfilePage;