const core = require('@actions/core');
const exec = require('@actions/exec');
const github = require('@actions/github');

function execute(){
    core.notice('This is a JS custom action');
}
execute();