#!/usr/bin/perl

use strict;
my @URLS_JSON;
my @URLS_CRUD;
my $INDENT = "        ";

foreach my $p_ (qx(find src/main/webapp/app -type f)) {
    chomp $p_;
    open(STDIN, $p_) or die "Ropen $p_ : $!";
    while (<STDIN>) {
	if (m!url:\s*[\'\"](\w+/json)!) {
	    push @URLS_JSON, "$INDENT<intercept-url pattern=\"/$1/**\" access=\"isAuthenticated()\" />";
	}
    }
    close STDIN;
}


foreach my $p_ (qx(find src/main/java/com/app/myschool/web -type f -name '*.java')) {
    chomp $p_;
    open(STDIN, $p_) or die "Ropen $p_ : $!";
    while (<STDIN>) {
	if (m!^\@RequestMapping\(\"(\S+)\"\)!) {
	    push @URLS_CRUD, "$INDENT<intercept-url pattern=\"$1\" access=\"hasRole('ROLE_ADMIN')\" />";
	    push @URLS_CRUD, "$INDENT<intercept-url pattern=\"$1/**\" access=\"hasRole('ROLE_ADMIN')\" />";
	}
    }
    close STDIN;
}

map { print "$_\n" } sort @URLS_JSON;
print "\n";
map { print "$_\n" } sort @URLS_CRUD;

exit 0;
