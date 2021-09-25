import React, {Component} from 'react';
import axios from 'axios';

export default class CreateTweet extends Component {

    constructor(props) {
        super(props);

        this.onChangeUserTweet = this.onChangeUserTweet.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.state = {
            tweet: '',
        }
    }

    onChangeUserTweet(e) {
        this.setState({
            tweet: e.target.value
        });
    }

    onSubmit(e) {
        e.preventDefault();
        const newTweet = {
            tweet: this.state.tweet,
        }
        let formData = new FormData(e.target);

        formData.append('text', this.state.tweet);   

        const config = {     
            headers: { 'content-type': 'multipart/form-data' }
        }
        console.log(formData)

        axios.post('http://132.145.52.5:8080/tweet', formData, config)
            .then(response => {
                console.log(response);
                alert("Please check Twitter. Your tweet: '" + newTweet.tweet + " ' was created")
            })
            .catch(error => {
                console.log(error);
            });
    }

    render() {
        return (
            <div>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <label>Enter Tweet: </label>
                        <input  type="text"
                                name = "posttweet"
                                className="form-control"
                                value={this.state.tweet}
                                onChange={this.onChangeUserTweet}
                                />
                    </div>
                    <div className="form-group">
                        <input type="submit" value="Create Tweet" className="btn btn-primary" />
                    </div>
                </form>
            </div>
        )
    }
}