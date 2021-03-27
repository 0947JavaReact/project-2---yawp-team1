import * as React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Navbar } from '../components/Navbar/Navbar';
import img1 from '../img/profile-picture-default.jpeg';
import UserCard from '../components/UserCard/UserCard';
import './FollowingPage.css';

function FollowingPage(props:any) {

    let [following, setFollowing] = React.useState<any>([]) 


    const username = props.match.params.username 

    React.useEffect(() => {
        
        setFollowing(getFollowing(username))
    }, [])

    const dispatch = useDispatch();

    const getFollowing = (username:string) => {

        //axios request
        const user1 = {
            username: "Bob_User",
            bio: "I am Bob",
            profilePic: img1
        };

        const user2 = {
            username: "Balut1",
            bio: "I am crunchy",
            profilePic: img1
        };

        const user3 = {
            username: "BuilderOfBobs2",
            bio: "I build",
            profilePic: img1
        };

        return [user1, user2, user3]
    }

    return (
        <div>
            <Navbar />
        <div className="following-page">
            <div className="following-container">
            <h1 className="following-h1">{`Who ${username} is following`}</h1>
            {following.map((user:any) => {
                
                return <UserCard username={user.username} bio={user.bio} profilePic={user.profilePic}></UserCard>
            })}
            </div>
        </div>
        </div>
    )
}

export default FollowingPage