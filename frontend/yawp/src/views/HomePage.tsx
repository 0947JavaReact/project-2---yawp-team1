import * as React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Navbar } from '../components/Navbar/Navbar';
import { fetchUserYawps } from '../actions/yawpActions';
import { fetchUser } from '../actions/userActions';
import YawpPost from '../components/YawpPost/YawpPost';
import CreateYawp from '../components/CreateYawp/CreateYawp';
import {useHistory} from 'react-router-dom';

function HomePage() {

    const history = useHistory()

    React.useEffect(() => {
        if(!localStorage.getItem('username')){
            history.push('/')
        }
        console.log(JSON.stringify(yawpState));
        getUser()
        getYawps()
    }, [])

    const yawpState = useSelector<any, any>((state) => state);
    //console.log(yawpState.yawp.items);

    const dispatch = useDispatch();

    const getUser = () => {
        dispatch(
            fetchUser(yawpState.user.user.username)
        )
    };

    const getYawps = () => {
        dispatch(
            fetchUserYawps("bob")
        )
    }

    return (
        <div className="home-page">
            <Navbar />
            <div className="container">
                <CreateYawp />

                {yawpState.yawp.items.map((item: any) => {
                    return (
                        <YawpPost id={item.id} username={item.username} content={item.content} profilePic={item.profilePic} likes={item.likes} key={item.id} />
                    )
                })}
            </div>
        </div>
    )
}

export default HomePage;
