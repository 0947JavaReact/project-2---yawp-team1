import * as React from 'react';
import './ProfilePage.css';
import { useSelector } from 'react-redux';
import Navbar from '../components/Navbar/Navbar';
import axios from 'axios';
import { UserCard } from '../components/UserCard/UserCard';
import './SearchPage.css';

function SearchPage(props: any) {
    let [users, setUsers] = React.useState<any>([]);
    const state = useSelector<any, any>((state) => state);
    const keyword = props.match.params.keyword;

    React.useEffect(() => {
        console.log(state.user.user.id);
        getUsers();
    }, [keyword, users.length, state.user.user]);

    const getUsers = async () => {
        let res = await axios.post(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/search`, {
            search: keyword
        });

        setUsers(res.data);
    }
    
    return (
        <div>
            <Navbar />
            <div className="search-page">
                <h1 className="search-page-h1">Search Results for term {keyword}</h1>
                <div className='search-page-users'>
                    {users.map((user: any) => {
                        return <UserCard id={user.userId} username={user.username} bio={user.bio} profilePic={user.picUrl}/>
                    })}
                </div>
            </div>
        </div>
    );
};

export default SearchPage;