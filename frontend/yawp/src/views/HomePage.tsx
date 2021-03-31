import * as React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Navbar } from '../components/Navbar/Navbar';
import { fetchFollowingPosts } from '../actions/yawpActions';
import { setUser } from '../actions/userActions';
import YawpPost from '../components/YawpPost/YawpPost';
import CreateYawp from '../components/CreateYawp/CreateYawp';
import {useHistory} from 'react-router-dom';
import './HomePage.css';

function HomePage() {
    const state = useSelector<any, any>((state) => state);
    const history = useHistory()

    React.useEffect(() => {
        if(state.user.user.id < 0){
            if(!localStorage.getItem('username')){
                history.push('/');
            }
            else{
                const user = {
                    username: localStorage.getItem("username"),
                    id: localStorage.getItem("id"),
                    bio: '',
                    profilePic: localStorage.getItem("profilePic"),
                    loggedIn: true,
                    loginAttempt: 'success'
                }
                getUser(user);
            }
        }
        if(state.user.user.id > 0){
            getYawps();
        }
    }, [state.user.user.username, state.yawp.items.length]);

    const dispatch = useDispatch();

    const getUser = (user:any) => {
        dispatch(
            setUser(user)
        )
    };

    const getYawps = () => {
        dispatch(
            fetchFollowingPosts(state.user.user.id)
        )
    }

    return (
        <div>
            <Navbar />
            <div className="home-page">
                <div className="home-container">
                    <CreateYawp />
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

export default HomePage;
