import React, { useState } from 'react';
import {useHistory} from 'react-router-dom';
import './SearchBar.css';
import SearchIcon from '@material-ui/icons/Search';

export const SearchBar: React.FC<any> = () => {
    let [keyword, setKeyword] = useState("");
    const history = useHistory();

    const onSearch = () => {
        history.push(`/search/${keyword}`);
    };

    return (
        <div className="search-bar-section">
            <input
                className="search-bar"
                value={keyword}
                placeholder="Search user"
                onChange={(e) => setKeyword(e.target.value)}
            />
            <button className="search-bar-button" onClick={onSearch}><SearchIcon style={{fontSize: 25}} /></button>
        </div>
    )
}

export default SearchBar;