import * as React from 'react';
import {Link} from 'react-router-dom';
import {ProfileHeader} from '../components/ProfileHeader/ProfileHeader';
import { useDispatch, useSelector } from 'react-redux';
import { fetchUser } from '../actions/userActions';
import { fetchUserYawps } from '../actions/yawpActions';
import YawpPost from '../components/YawpPost/YawpPost';


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
            <ProfileHeader username={username}></ProfileHeader>
            <h1>{props.match.params.username}</h1>
            {state.yawp.items.map((item: any) => {
                    return (
                        <YawpPost id={item.id} username={item.username} content={item.content} profilePic={item.profilePic} likes={item.likes} key={item.id} />
                    )
                })}
        </div>

    )
}

export default ProfilePage;