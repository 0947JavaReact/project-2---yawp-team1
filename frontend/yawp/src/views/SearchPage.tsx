import * as React from 'react';
import './ProfilePage.css';
import { useSelector } from 'react-redux';
import Navbar from '../components/Navbar/Navbar';
import axios from 'axios';
import { UserCard } from '../components/UserCard/UserCard';

function SearchPage(props: any) {
    let [loggedInFollowers, setLoggedInFollowers] = React.useState<any>([]);
    let [users, setUsers] = React.useState<any>([]);
    const state = useSelector<any, any>((state) => state);
    const keyword = props.match.params.keyword;

    React.useEffect(() => {
        getUsers();
        getLoggedInFollowers();
    }, [keyword, loggedInFollowers.length]);

    const getUsers = async () => {
        let res = await axios.post(`http://localhost:9025/users/search`, {
            search: keyword
        });
        setUsers(res.data);
    }

    const getLoggedInFollowers = async () => {
        let res = await axios.post('http://localhost:9025/users/followers', {
            user_id: state.user.user.id
        });

        setLoggedInFollowers(res.data);
    }
    return (
        <div>
            <Navbar />
            {users.map((user: any) => {
                return <UserCard id={user.userId} username={user.username} bio={user.bio} profilePic={user.picUrl} showFollowButton={loggedInFollowers.includes(user)} />
            })}
        </div>
    );
};

export default SearchPage;