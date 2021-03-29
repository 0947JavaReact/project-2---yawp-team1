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
        if(user){
            getYawps();
        }
    }, [user.userId])

    const getUser = async () => {
        console.log("above the get data");
        let res = await axios.get(`http://localhost:9025/users/username/${username}`);
        console.log(res.data);
        setUser(res.data);
        console.log(user);
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
    const getYawps = () => {
        console.log(user);
        dispatch(
            fetchUserYawps(user.userId)
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
                                <YawpPost id={item.yawpId} username={"Dont have yet"} content={item.message} profilePic={item.profilePic} likes={item.likes.length} key={item.yawpId} />
                            )
                        })}
                </div>
            </div>
        </div>

    )
}

export default ProfilePage;