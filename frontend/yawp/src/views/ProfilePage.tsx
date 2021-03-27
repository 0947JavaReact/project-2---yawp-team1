import * as React from 'react';
import {Link} from 'react-router-dom';
import {ProfileHeader} from '../components/ProfileHeader/ProfileHeader';
import { useDispatch, useSelector } from 'react-redux';
import { fetchUser } from '../actions/userActions';
import { fetchUserYawps } from '../actions/yawpActions';
import YawpPost from '../components/YawpPost/YawpPost';
import './ProfilePage.css';
import Navbar from '../components/Navbar/Navbar';

function ProfilePage(props:any) {

    React.useEffect(() => {
        getUser()
        getYawps()
    }, [])

    const username = props.match.params.username 

    console.log(props.match)

    const state = useSelector<any, any>((state) => state);

    const dispatch = useDispatch();

    const getUser = () => {
        dispatch(
            fetchUser(username)
        )
    };

    const getYawps = () => {
        dispatch(
            fetchUserYawps(username)
        )
    }

    return(
        <div>
            <Navbar />
            <div className="profile-page">
                <div className="profile-container">
                    <ProfileHeader username={username}></ProfileHeader>
                    {state.yawp.items.map((item: any) => {
                            return (
                                <YawpPost id={item.id} username={item.username} content={item.content} profilePic={item.profilePic} likes={item.likes} key={item.id} />
                            )
                        })}
                </div>
            </div>
        </div>

    )
}

export default ProfilePage;