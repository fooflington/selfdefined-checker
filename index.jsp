<!DOCTYPE html>
<html>
<head>
    <title>Language Checker</title>
    <link rel="stylesheet" type="text/css" href="base.css" />
    <link href="https://fonts.googleapis.com/css2?family=Fira+Mono:wght@400;500&family=Roboto:wght@500&display=swap" rel="stylesheet">
</head>
<body>
    <h1>Language checker</h1>
    <p>Language checker based on the <a href="https://www.selfdefined.app/">Self Defined</a> project</p>
    <blockquote>
        Self-Defined seeks to provide more inclusive, holistic, and fluid definitions to reflect the diverse perspectives of the modern world.

        With the foundation of vocabulary, we can begin to understand lived experiences of people different than us. Words can provide us with a sense of identify and allow us to find kinship through common experiences.
    </blockquote>
    <form action="Checker" method="post">
        <!--<label for="input">Text to be checked</label>-->
        <textarea id="input" size="25" name="input" rows="20" cols="120" value=""></textarea>
        <input type="submit" value="Check" />
    </form>
    <p>You can also POST to the <tt>/Checker</tt> endpoint specifying <tt>Accept: text/json</tt>.</p>
</body>