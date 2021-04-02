import * as React from 'react';
import './ProfilePage.css';
import { useSelector } from 'react-redux';
import Navbar from '../components/Navbar/Navbar';
import axios from 'axios';
import { UserCard } from '../components/UserCard/UserCard';
import './SearchPage.css';

function SearchPage(props: any) {
    let [loggedInFollowers, setLoggedInFollowers] = React.useState<any>([]);
    let [users, setUsers] = React.useState<any>([]);
    const state = useSelector<any, any>((state) => state);
    const keyword = props.match.params.keyword;

    React.useEffect(() => {
        console.log(state.user.user.id);
        getUsers();
        getLoggedInFollowers();
    }, [keyword, users.length, loggedInFollowers.length, state.user.user]);

    const getUsers = async () => {
        let res = await axios.post(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/search`, {
            search: keyword
        });

        setUsers(res.data);
    }

    const getLoggedInFollowers = async () => {
        let res = await axios.post('http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/following', {
            user_id: state.user.user.id
        });

        let loggedInFollowerIds = [];
        for (let i = 0; i < res.data.length; i++) {
            loggedInFollowerIds.push(res.data[i].userId);
        }

        setLoggedInFollowers(loggedInFollowerIds);
    }
    
    return (
        <div>
            <Navbar />
            <div className="search-page">
                <h1 className="search-page-h1">Search Results for term {keyword}</h1>
                <div className='search-page-users'>
                    {users.map((user: any) => {
                        console.log(user.userId);
                        console.log(loggedInFollowers);
                        return <UserCard id={user.userId} username={user.username} bio={user.bio} profilePic={user.picUrl} showFollowButton={loggedInFollowers.includes(user.userId)} incLogginFollowers={()=>setLoggedInFollowers(loggedInFollowers.push(user.id))}/>
                    })}
                </div>
            </div>
        </div>
    );
};

export default SearchPage;