#!/usr/bin/env perl
############################################################################
##	populate.pl
##  Perl script
##
## Sample parameter file:  See perllib::MyConfig.pm.
############################################################################
##
##  MODIFICATION HISTORY:
##  DATE        WHOM                DESCRIPTION
##  07/31/2011  Denis M. Putnam     Created.
##	$History: $
############################################################################

use strict;
use warnings;
#use diagnostics;

use perllib::Funcs;
use Getopt::Long;
use Carp;
use Cwd;
use Data::Dumper;
use File::Basename;
use File::Path qw(mkpath rmtree);
use perllib::Command;
use perllib::MyConfig;
use perllib::MyMimeEmail;
use Sys::Hostname;

##################################
#	Function forward declarations
##################################
sub initialize();
sub getCmdOptions();
sub resetInit(\%);
sub printVersionInfo();
sub doWork();
sub cleanUp();
sub getIDs($);
sub deleteTableData($$$);
sub deleteAllTableData($);
sub insertFaculty();
sub insertStudents();

###################################
#   Begin MAIN section.
###################################
###################################
#   Declare my CONFIG hash.
###################################
my %CONFIG;

################################################################################
#   Initialize the program.
################################################################################
initialize();

## Populate command-line options
getCmdOptions();

## Print file version info
printVersionInfo();

my $rc;
$rc = doWork();

cleanUp();
exit( $rc );
###################################
#   End MAIN section.
###################################


###################################
#   Begin subroutine section.
###################################
###############################################################################
#   sendMail()
#
#   DESCRIPTION:
#       Send email attachment.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub sendMail()
{
	my $rc = 0;
	my $subject = $CONFIG{batch_id} . " change log validation.";
	my $message = "Change log validation for the " . $CONFIG{batch_id} . " " . $CONFIG{config};
	my $mailObject = new perllib::MyMimeEmail(
									from_address	=> $CONFIG{email_from},
									to_address		=> $CONFIG{email_to},
									subject			=> $subject,
									message			=> $message,
									attachment		=> $CONFIG{file},
									attach_type		=> $CONFIG{email_attachment_type},
									funcs_object	=> $CONFIG{funcs}
	);
	if( defined( $mailObject ) )
	{
		$mailObject->send();
	}
	else
	{
		logIt( "main::sendMail(): Failed to instantiate the perllib::MyMimeEmail object.\n" );
		return 1
	}
	return $rc;
}
###############################################################################
#   getBaseStudentRecordHash()
#
#   DESCRIPTION:
#       Get the base student record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseStudentRecordHash()
{
	my $rc = 0;
	#'{address1:"9999 Bogus Drive",address2:"NONE",city:"Gaithersburg",country:"US",firstName:"Denis",lastName:"Putnam",lastUpdated:"07/07/13",middleName:"M",personID:"1111",phone1:"999-999-9999",phone2:"NONE",postalCode:"99999",province:"MD",version:0,whoUpdated:"denis"}'
	my %h;
	$h{personID} = 1;
	$h{firstName} = "firstname";
	$h{lastName} = "lastname";
	$h{middleName} = "middlename";
	$h{address1} = "address";
	$h{address2} = "address";
	$h{city} = "city";
	$h{province} = "province";
	$h{postalCode} = "99999";
	$h{country} = "country";
	$h{phone1} = "999-999-9999";
	$h{phone2} = "888-888-8888";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertStudents()
#
#   DESCRIPTION:
#       Insert student records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertStudents()
{
	my $rc = 0;
	for( my $i = 1; $i <= 100; $i++ )
	{
		my %h = getBaseStudentRecordHash();
		$h{personID} = $i;
		$h{firstName} .= $i;
		$h{lastName} .= $i;
		$h{middleName} .= $i;
		$h{address1} .= $i;
		$h{address2} .= $i;
		$h{city} .= $i;
		$h{province} .= $i;
		#$h{postalCode} = "99999";
		#$h{country} = "country";
		#$h{phone1} = "999-999-9999";
		#$h{phone2} = "888-888-8888";
		#$h{lastUpdated} = "07/12/13";
		#$h{whoUpdate} = "populate";

		my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/students -d \'{";
		$command .= "personID:\"" . $h{personID} . "\",";
		$command .= "firstName:\"" . $h{firstName} . "\",";
		$command .= "lastName:\"" . $h{lastName} . "\",";
		$command .= "middleName:\"" . $h{middleName} . "\",";
		$command .= "address1:\"" . $h{address1} . "\",";
		$command .= "address2:\"" . $h{address2} . "\",";
		$command .= "city:\"" . $h{city} . "\",";
		$command .= "province:\"" . $h{province} . "\",";
		$command .= "postalCode:\"" . $h{postalCode} . "\",";
		$command .= "country:\"" . $h{country} . "\",";
		$command .= "phone1:\"" . $h{phone1} . "\",";
		$command .= "phone2:\"" . $h{phone2} . "\",";
		$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
		$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
		logIt( "main::insertStudents(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::insertStudents(): $line\n" );
		}
	}
}
###############################################################################
#   getBaseSubjectRecordHash()
#
#   DESCRIPTION:
#       Get the base student record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseSubjectRecordHash()
{
	my $rc = 0;
	my %h;
	$h{name} = "subject";
	$h{gradeLevel} = 1;
	$h{creditHours} = 3;
	$h{description} = "description";
	$h{objectives} = "objectives";
	$h{completed} = "false";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertSubjects()
#
#   DESCRIPTION:
#       Insert student records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertSubjects()
{
	my $rc = 0;
	for( my $i = 1; $i <= 100; $i++ )
	{
		my %h = getBaseSubjectRecordHash();
		$h{name} .= $i;
		$h{description} .= $i;
		$h{objectives} .= $i;

		my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/subjects -d \'{";
		$command .= "name:\"" . $h{name} . "\",";
		$command .= "gradeLevel:" . $h{gradeLevel} . ",";
		$command .= "creditHours:" . $h{creditHours} . ",";
		$command .= "description:\"" . $h{description} . "\",";
		$command .= "objectives:\"" . $h{objectives} . "\",";
		$command .= "completed:" . $h{completed} . ",";
		$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
		$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
		logIt( "main::insertSubjects(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::insertSubjects(): $line\n" );
		}
	}
}
###############################################################################
#   getBasePreviousTranscriptsRecordHash()
#
#   DESCRIPTION:
#       Get the base record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBasePreviousTranscriptsRecordHash()
{
	my $rc = 0;
	my %h;
	$h{type} = 1;
	$h{dateEntered} = "07/12/13";
	$h{fileName} = "filename";
	$h{comments} = "comment";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertPreviousTranscripts()
#
#   DESCRIPTION:
#       Insert records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertPreviousTranscripts()
{
	my $rc = 0;
	for( my $i = 1; $i <= 100; $i++ )
	{
		my %h = getBasePreviousTranscriptsRecordHash();
		$h{fileName} .= $i;
		$h{comments} .= $i;

		my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/previoustranscriptses -d \'{";
		$command .= "fileName:\"" . $h{fileName} . "\",";
		$command .= "comments:\"" . $h{comments} . "\",";
		$command .= "type:" . $h{type} . ",";
		$command .= "dateEntered:\"" . $h{dateEntered} . "\",";
		$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
		$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
		logIt( "main::insertPreviousTranscripts(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::insertPreviousTranscripts(): $line\n" );
		}
	}
}
###############################################################################
#   getBaseGraduateTrackingRecordHash()
#
#   DESCRIPTION:
#       Get the base record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseGraduateTrackingRecordHash()
{
	my $rc = 0;
	my %h;
	$h{schoolAttended} = "schoolname";
	$h{email} = "email";
	$h{comments} = "comment";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertGraduateTracking()
#
#   DESCRIPTION:
#       Insert records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertGraduateTracking()
{
	my $rc = 0;
	for( my $i = 1; $i <= 100; $i++ )
	{
		my %h = getBaseGraduateTrackingRecordHash();
		$h{schoolAttended} .= $i;
		$h{email} .= $i;
		$h{comments} .= $i;

		my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/graduatetrackings -d \'{";
		$command .= "schoolAttended:\"" . $h{schoolAttended} . "\",";
		$command .= "email:\"" . $h{email} . "\",";
		$command .= "comments:\"" . $h{comments} . "\",";
		$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
		$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
		logIt( "main::insertGraduateTracking(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::insertGraduateTracking(): $line\n" );
		}
	}
}
###############################################################################
#   getBaseArtifactsRecordHash()
#
#   DESCRIPTION:
#       Get the base record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseArtifactsRecordHash()
{
	my $rc = 0;
	my %h;
	$h{artifactName} = "artifact_name";
	$h{fileName} = "fileName";
	$h{comments} = "comment";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertArtifacts()
#
#   DESCRIPTION:
#       Insert records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertArtifacts()
{
	my $rc = 0;
	for( my $i = 1; $i <= 100; $i++ )
	{
		my %h = getBaseArtifactsRecordHash();
		$h{artifactName} .= $i;
		$h{fileName} .= $i;
		$h{comments} .= $i;

		my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/artifacts -d \'{";
		$command .= "artifactName:\"" . $h{artifactName} . "\",";
		$command .= "fileName:\"" . $h{fileName} . "\",";
		$command .= "comments:\"" . $h{comments} . "\",";
		$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
		$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
		logIt( "main::insertArtifacts(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::insertArtifacts(): $line\n" );
		}
	}
}
###############################################################################
#   getBaseBodyOfWorkRecordHash()
#
#   DESCRIPTION:
#       Get the base record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseBodyOfWorkRecordHash()
{
	my $rc = 0;
	my %h;
	$h{workName} = "works_name";
	$h{description} = "description";
	$h{what} = "what";
	$h{objective} = "objective";
	$h{locked} = "false";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertBodyOfWork()
#
#   DESCRIPTION:
#       Insert records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertBodyOfWork()
{
	my $rc = 0;
	for( my $i = 1; $i <= 100; $i++ )
	{
		my %h = getBaseBodyOfWorkRecordHash();
		$h{workName} .= $i;
		$h{description} .= $i;
		$h{what} .= $i;
		$h{objective} .= $i;

		my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/bodyofworks -d \'{";
		$command .= "workName:\"" . $h{workName} . "\",";
		$command .= "description:\"" . $h{description} . "\",";
		$command .= "what:\"" . $h{what} . "\",";
		$command .= "objective:\"" . $h{objective} . "\",";
		$command .= "locked:" . $h{locked} . ",";
		$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
		$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
		logIt( "main::insertBodyOfWork(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::insertBodyOfWork(): $line\n" );
		}
	}
}
###############################################################################
#   getBaseEvaluationRatingsRecordHash()
#
#   DESCRIPTION:
#       Get the base record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseEvaluationRatingsRecordHash()
{
	my $rc = 0;
	my %h;
	$h{motivation} = "6";
	$h{organization} = "6";
	$h{effectiveUseOfStudyTime} = "6";
	$h{qualityOfWork} = "6";
	$h{accuracyOfWork} = "6";
	$h{complexityOfWork} = "6";
	$h{growth} = "6";
	$h{consistency} = "6";
	$h{locked} = "false";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertEvaluationRatings()
#
#   DESCRIPTION:
#       Insert records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertEvaluationRatings()
{
	my $rc = 0;
	for( my $i = 1; $i <= 100; $i++ )
	{
		my %h = getBaseEvaluationRatingsRecordHash();

		my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/evaluationratingses -d \'{";
		$command .= "motivation:" . $h{motivation} . ",";
		$command .= "organization:" . $h{organization} . ",";
		$command .= "effectiveUseOfStudyTime:" . $h{effectiveUseOfStudyTime} . ",";
		$command .= "qualityOfWork:" . $h{qualityOfWork} . ",";
		$command .= "accuracyOfWork:" . $h{accuracyOfWork} . ",";
		$command .= "complexityOfWork:" . $h{complexityOfWork} . ",";
		$command .= "growth:" . $h{growth} . ",";
		$command .= "consistency:" . $h{consistency} . ",";
		$command .= "locked:" . $h{locked} . ",";
		$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
		$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
		logIt( "main::insertEvaluationRatings(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::insertEvaluationRatings(): $line\n" );
		}
	}
}
###############################################################################
#   getBaseQuarterRecordHash()
#
#   DESCRIPTION:
#       Get the base record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseQuarterRecordHash()
{
	my $rc = 0;
	my %h;
	$h{qtrName} = "quarter";
	$h{gradeType} = "1";
	$h{grade} = "3.5";
	$h{locked} = "false";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertQuarter()
#
#   DESCRIPTION:
#       Insert records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertQuarter()
{
	my $rc = 0;
	for( my $i = 1; $i <= 25; $i++ )
	{
		my %h = getBaseQuarterRecordHash();
		$h{qtrName} .= $i;

		my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/quarters -d \'{";
		$command .= "qtrName:\"" . $h{qtrName} . "\",";
		$command .= "gradeType:" . $h{gradeType} . ",";
		$command .= "grade:" . $h{grade} . ",";
		$command .= "locked:" . $h{locked} . ",";
		$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
		$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
		logIt( "main::insertQuarter(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::insertQuarter(): $line\n" );
		}
	}
}
###############################################################################
#   getBaseSkillRatingsRecordHash()
#
#   DESCRIPTION:
#       Get the base record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseSkillRatingsRecordHash()
{
	my $rc = 0;
	my %h;
	$h{remembering} = "6";
	$h{understanding} = "6";
	$h{applying} = "6";
	$h{analyzing} = "6";
	$h{creating} = "6";
	$h{locked} = "false";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertSkillRatings()
#
#   DESCRIPTION:
#       Insert records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertSkillRatings()
{
	my $rc = 0;
	for( my $i = 1; $i <= 25; $i++ )
	{
		my %h = getBaseSkillRatingsRecordHash();
		$h{qtrName} .= $i;

		my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/skillratingses -d \'{";
		$command .= "remembering:" . $h{remembering} . ",";
		$command .= "understanding:" . $h{understanding} . ",";
		$command .= "applying:" . $h{applying} . ",";
		$command .= "analyzing:" . $h{analyzing} . ",";
		$command .= "creating:" . $h{creating} . ",";
		$command .= "locked:" . $h{locked} . ",";
		$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
		$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
		logIt( "main::insertSkillRatings(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::insertSkillRatings(): $line\n" );
		}
	}
}
###############################################################################
#   getBaseWeeklyRecordHash()
#
#   DESCRIPTION:
#       Get the base record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseWeeklyRecordHash()
{
	my $rc = 0;
	my %h;
	$h{week_year} = "2013";
	$h{week_month} = "7";
	$h{week_number} = "3";
	$h{locked} = "false";
	$h{comments} = "comment";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertWeekly()
#
#   DESCRIPTION:
#       Insert records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertWeekly()
{
	my $rc = 0;
	for( my $year = 2013; $year <= 2014; $year++ )
	{
		my %h = getBaseWeeklyRecordHash();

		for( my $month = 1; $month <= 12; $month++ )
		{
			for( my $week = 1; $week <= 4; $week++ )
			{
				my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/weeklys -d \'{";
				$h{week_year} = $year;
				$h{week_month} = $month;
				$h{week_number} = $week;
				$h{comments} = "comment" . $year . $month . $week;
				$command .= "week_year:" . $h{week_year} . ",";
				$command .= "week_month:" . $h{week_month} . ",";
				$command .= "week_number:" . $h{week_number} . ",";
				$command .= "comments:\"" . $h{comments} . "\",";
				$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
				$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
				logIt( "main::insertWeekly(): command=$command\n" );
				my( $count, $rc, @res ) = runCommand( $command );
				foreach my $line ( @res )
				{
					logIt( "main::insertWeekly(): $line\n" );
				}
			}
		}
	}
}
###############################################################################
#   getBaseMonthlyEvaluationRatingsRecordHash()
#
#   DESCRIPTION:
#       Get the base record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseMonthlyEvaluationRatingsRecordHash()
{
	my $rc = 0;
	my %h;
	$h{month_year} = "2013";
	$h{month_number} = "7";
	$h{week_number} = "3";
	$h{levelOfDifficulty} = "6";
	$h{criticalThinkingSkills} = "6";
	$h{effectiveCorrectionActions} = "6";
	$h{accuratelyIdCorrections} = "6";
	$h{completingCourseObjectives} = "6";
	$h{thoughtfulnessOfReflections} = "6";
	$h{responsibilityOfProgress} = "6";
	$h{workingEffectivelyWithAdvisor} = "6";
	$h{locked} = "false";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertMonthlyEvaluationRatings()
#
#   DESCRIPTION:
#       Insert records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertMonthlyEvaluationRatings()
{
	my $rc = 0;
	for( my $year = 2013; $year <= 2014; $year++ )
	{
		my %h = getBaseMonthlyEvaluationRatingsRecordHash();

		for( my $month = 1; $month <= 12; $month++ )
		{
			for( my $week = 1; $week <= 4; $week++ )
			{
				my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/monthlyevaluationratingses -d \'{";
				$h{month_year} = $year;
				$h{month_number} = $month;
				$h{week_number} = $week;
				$command .= "month_year:" . $h{month_year} . ",";
				$command .= "month_number:" . $h{month_number} . ",";
				$command .= "week_number:" . $h{week_number} . ",";
				$command .= "levelOfDifficulty:" . $h{levelOfDifficulty} . ",";
				$command .= "criticalThinkingSkills:" . $h{criticalThinkingSkills} . ",";
				$command .= "effectiveCorrectionActions:" . $h{effectiveCorrectionActions} . ",";
				$command .= "accuratelyIdCorrections:" . $h{accuratelyIdCorrections} . ",";
				$command .= "completingCourseObjectives:" . $h{completingCourseObjectives} . ",";
				$command .= "thoughtfulnessOfReflections:" . $h{thoughtfulnessOfReflections} . ",";
				$command .= "responsibilityOfProgress:" . $h{responsibilityOfProgress} . ",";
				$command .= "workingEffectivelyWithAdvisor:" . $h{workingEffectivelyWithAdvisor} . ",";
				$command .= "locked:" . $h{locked} . ",";
				$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
				$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
				logIt( "main::insertMonthlyEvaluationRatings(): command=$command\n" );
				my( $count, $rc, @res ) = runCommand( $command );
				foreach my $line ( @res )
				{
					logIt( "main::insertMonthlyEvaluationRatings(): $line\n" );
				}
			}
		}
	}
}
###############################################################################
#   getBaseMonthlySummaryRatingsRecordHash()
#
#   DESCRIPTION:
#       Get the base record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseMonthlySummaryRatingsRecordHash()
{
	my $rc = 0;
	my %h;
	$h{year_number} = "2013";
	$h{month_number} = "7";
	$h{week_number} = "3";
	$h{feelings} = "feelings";
	$h{reflections} = "reflections";
	$h{patternsOfCorrections} = "patterns_of_corrections";
	$h{effectivenessOfActions} = "effectiveness_of_actions";
	$h{actionResults} = "action_results";
	$h{realizations} = "realizations";
	$h{plannedChanges} = "planned_changes";
	$h{comments} = "comments";
	$h{locked} = "false";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertMonthlySummaryRatings()
#
#   DESCRIPTION:
#       Insert records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertMonthlySummaryRatings()
{
	my $rc = 0;
	for( my $year = 2013; $year <= 2014; $year++ )
	{
		my %h = getBaseMonthlySummaryRatingsRecordHash();

		for( my $month = 1; $month <= 12; $month++ )
		{
			for( my $week = 1; $week <= 4; $week++ )
			{
				my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/monthlysummaryratingses -d \'{";
				$h{year_number} = $year;
				$h{month_number} = $month;
				$h{week_number} = $week;
				$h{feelings} = "feelings" . $year . $month . $week;
				$h{reflections} = "reflections" . $year . $month . $week;
				$h{patternsOfCorrections} = "patterns_of_corrections" . $year . $month . $week;
				$h{effectivenessOfActions} = "effectiveness_of_actions" . $year . $month . $week;
				$h{actionResults} = "action_results" . $year . $month . $week;
				$h{realizations} = "realizations" . $year . $month . $week;
				$h{plannedChanges} = "planned_changes" . $year . $month . $week;
				$h{comments} = "comment" . $year . $month . $week;
				$command .= "year_number:" . $h{year_number} . ",";
				$command .= "month_number:" . $h{month_number} . ",";
				$command .= "week_number:" . $h{week_number} . ",";
				$command .= "feelings:" . $h{feelings} . ",";
				$command .= "reflections:" . $h{reflections} . ",";
				$command .= "patternsOfCorrections:" . $h{patternsOfCorrections} . ",";
				$command .= "effectivenessOfActions:" . $h{effectivenessOfActions} . ",";
				$command .= "actionResults:" . $h{actionResults} . ",";
				$command .= "realizations:" . $h{realizations} . ",";
				$command .= "plannedChanges:" . $h{plannedChanges} . ",";
				$command .= "comments:" . $h{comments} . ",";
				$command .= "locked:" . $h{locked} . ",";
				$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
				$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
				logIt( "main::insertMonthlySummaryRatings(): command=$command\n" );
				my( $count, $rc, @res ) = runCommand( $command );
				foreach my $line ( @res )
				{
					logIt( "main::insertMonthlySummaryRatings(): $line\n" );
				}
			}
		}
	}
}
###############################################################################
#   getBaseDailyRecordHash()
#
#   DESCRIPTION:
#       Get the base record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseDailyRecordHash()
{
	my $rc = 0;
	my %h;
	$h{daily_year} = "2013";
	$h{daily_month} = "7";
	$h{daily_week} = "3";
	$h{daily_day} = "3";
	$h{daily_hours} = "1.5";
	$h{locked} = "false";
	$h{resourcesUsed} = "resource";
	$h{studyDetails} = "study_detail";
	$h{evaluation} = "evaluation";
	$h{correction} = "correction";
	$h{dailyAction} = "daily_action";
	$h{comments} = "comment";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertDaily()
#
#   DESCRIPTION:
#       Insert records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertDaily()
{
	my $rc = 0;
	for( my $year = 2013; $year <= 2014; $year++ )
	{
		my %h = getBaseDailyRecordHash();

		for( my $month = 1; $month <= 12; $month++ )
		{
			for( my $week = 1; $week <= 4; $week++ )
			{
				for( my $day = 1; $day <= 5; $day++ )
				{
					my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/dailys -d \'{";
					$h{daily_year} = $year;
					$h{daily_month} = $month;
					$h{daily_week} = $week;
					$h{daily_day} = $day;
					$h{resourcesUsed} = "resource" . $year . $month . $week . $day;
					$h{studyDetails} = "study_details" . $year . $month . $week . $day;
					$h{evaluation} = "evaluation" . $year . $month . $week . $day;
					$h{correction} = "correction" . $year . $month . $week . $day;
					$h{dailyAction} = "daily_action" . $year . $month . $week . $day;
					$h{comments} = "comment" . $year . $month . $week . $day;
					$command .= "daily_year:" . $h{daily_year} . ",";
					$command .= "daily_month:" . $h{daily_month} . ",";
					$command .= "daily_week:" . $h{daily_week} . ",";
					$command .= "daily_day:" . $h{daily_day} . ",";
					$command .= "daily_hours:" . $h{daily_hours} . ",";
					$command .= "resourcesUsed:\"" . $h{resourcesUsed} . "\",";
					$command .= "studyDetails:\"" . $h{studyDetails} . "\",";
					$command .= "evaluation:\"" . $h{evaluation} . "\",";
					$command .= "correction:\"" . $h{correction} . "\",";
					$command .= "dailyAction:\"" . $h{dailyAction} . "\",";
					$command .= "comments:\"" . $h{comments} . "\",";
					$command .= "locked:" . $h{locked} . ",";
					$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
					$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
					logIt( "main::insertDaily(): command=$command\n" );
					my( $count, $rc, @res ) = runCommand( $command );
					foreach my $line ( @res )
					{
						logIt( "main::insertDaily(): $line\n" );
					}
				}
			}
		}
	}
}
###############################################################################
#   getBaseFacultyRecordHash()
#
#   DESCRIPTION:
#       Get the base student record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseFacultyRecordHash()
{
	my $rc = 0;
	#'{address1:"9999 Bogus Drive",address2:"NONE",city:"Gaithersburg",country:"US",firstName:"Denis",lastName:"Putnam",lastUpdated:"07/07/13",middleName:"M",personID:"1111",phone1:"999-999-9999",phone2:"NONE",postalCode:"99999",province:"MD",version:0,whoUpdated:"denis"}'
	my %h;
	$h{personID} = 1;
	$h{firstName} = "fac_firstname";
	$h{lastName} = "facy_lastname";
	$h{middleName} = "fac_middlename";
	$h{address1} = "fac_address";
	$h{address2} = "fac_address";
	$h{city} = "fac_city";
	$h{province} = "fac_province";
	$h{postalCode} = "99999";
	$h{country} = "country";
	$h{phone1} = "999-999-9999";
	$h{phone2} = "888-888-8888";
	$h{lastUpdated} = "07/12/13";
	$h{whoUpdate} = "populate";
	return %h;
}
###############################################################################
#   insertFaculty()
#
#   DESCRIPTION:
#       Insert student records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertFaculty()
{
	my $rc = 0;
	for( my $i = 1; $i <= 100; $i++ )
	{
		my %h = getBaseFacultyRecordHash();
		$h{personID} = $i;
		$h{firstName} .= $i;
		$h{lastName} .= $i;
		$h{middleName} .= $i;
		$h{address1} .= $i;
		$h{address2} .= $i;
		$h{city} .= $i;
		$h{province} .= $i;
		#$h{postalCode} = "99999";
		#$h{country} = "country";
		#$h{phone1} = "999-999-9999";
		#$h{phone2} = "888-888-8888";
		#$h{lastUpdated} = "07/12/13";
		#$h{whoUpdate} = "populate";

		my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/facultys -d \'{";
		$command .= "personID:\"" . $h{personID} . "\",";
		$command .= "firstName:\"" . $h{firstName} . "\",";
		$command .= "lastName:\"" . $h{lastName} . "\",";
		$command .= "middleName:\"" . $h{middleName} . "\",";
		$command .= "address1:\"" . $h{address1} . "\",";
		$command .= "address2:\"" . $h{address2} . "\",";
		$command .= "city:\"" . $h{city} . "\",";
		$command .= "province:\"" . $h{province} . "\",";
		$command .= "postalCode:\"" . $h{postalCode} . "\",";
		$command .= "country:\"" . $h{country} . "\",";
		$command .= "phone1:\"" . $h{phone1} . "\",";
		$command .= "phone2:\"" . $h{phone2} . "\",";
		$command .= "lastUpdated:\"" . $h{lastUpdated} . "\",";
		$command .= "whoUpdated:\"" . $h{whoUpdate} . "\"}\'";
		logIt( "main::insertFaculty(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::insertFaculty(): $line\n" );
		}
	}
}
###############################################################################
#   getBaseRolesRecordHash()
#
#   DESCRIPTION:
#       Get the base student record.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getBaseRolesRecordHash()
{
	my $rc = 0;
	my %h;
	$h{roleType} = 1;
	$h{roleName} = "role_name";
	return %h;
}
###############################################################################
#   insertRoles()
#
#   DESCRIPTION:
#       Insert student records.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub insertRoles()
{
	my $rc = 0;
	for( my $i = 0; $i < 4; $i++ )
	{
		my %h = getBaseRolesRecordHash();
		$h{roleName} .= $i;
		$h{roleType} = $i;

		my $command = "curl -v -X POST -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/roleses -d \'{";
		$command .= "roleName:\"" . $h{roleName} . "\",";
		$command .= "roleType:\"" . $h{roleType} . "\"}\'";
		logIt( "main::insertRoles(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::insertRoles(): $line\n" );
		}
	}
}
###############################################################################
#   deleteTableData()
#
#   DESCRIPTION:
#       Delete the given table data based on the given range of ids.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub deleteTableData($$$)
{
	my( $tableName, $start, $end ) = @_;
	my $rc = 0;
	for( my $id = $start; $id <= $end; $id++ )
	{
		my $command = "curl -v -X DELETE -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/$tableName/$id";
		#my $command = "curl -v -X DELETE http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/$tableName/$id";
		logIt( "main::deleteTableData(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::deleteTableData(): $line\n" );
		}
	}
}
###############################################################################
#   deleteAllTableData()
#
#   DESCRIPTION:
#       Delete the given table data
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub deleteAllTableData($)
{
	my( $tableName ) = @_;
	my $rc = 0;
	my @IDS = getIDs( $tableName );
	foreach my $id ( @IDS )
	{
		my $command = "curl -v -X DELETE -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/$tableName/$id";
		#my $command = "curl -v -X DELETE http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/$tableName/$id";
		logIt( "main::deleteAllTableData(): command=$command\n" );
		my( $count, $rc, @res ) = runCommand( $command );
		foreach my $line ( @res )
		{
			logIt( "main::deleteAllTableData(): $line\n" );
		}
	}
}
###############################################################################
#   getIDs()
#
#   DESCRIPTION:
#       Get the ID's of the given table name.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getIDs($)
{
	my( $tableName ) = @_;
	#my $rc = 0;
	my @ar;
	my $command = "curl -v -X GET -H \"Accept: application/json\" http://$CONFIG{host}:$CONFIG{port}/$CONFIG{app}/$tableName";
	logIt( "main::getIDs(): command=$command\n" );
	my( $count, $rc, @res ) = runCommand( $command );
	foreach my $line ( @res )
	{
		logIt( "main::getIDs(): $line\n" );
		my @records = split( /\},\{/, $line );
		logIt( "main::getIDs(): records=" . Dumper( \@records ) . "\n" );
		foreach my $col ( @records ) 
		{
			my @columns = split( /,/, $col );
			foreach my $token ( @columns )
			{
				if( $token =~ m/\"id\":/ )
				{
					my( $name, $id ) = split( /:/, $token );
					push( @ar, $id );
				}
			}
		}
	}
	logIt( "main::getIDs(): ar=" . Dumper( \@ar ) . "\n" );
	return @ar;

}
###############################################################################
#   doWork()
#
#   DESCRIPTION:
#       Does the main work for this program.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub doWork()
{
	my $rc = 0;

	printInfo();
	deleteAllTableData( "students" );
	deleteAllTableData( "facultys" );
	deleteAllTableData( "roleses" );
	deleteAllTableData( "subjects" );
	deleteAllTableData( "previoustranscriptses" );
	deleteAllTableData( "graduatetrackings" );
	deleteAllTableData( "weeklys" );
	deleteAllTableData( "artifacts" );
	deleteAllTableData( "bodyofworks" );
	deleteAllTableData( "dailys" );
	deleteAllTableData( "monthlyevaluationratingses" );
	deleteAllTableData( "monthlysummaryratingses" );
	deleteAllTableData( "evaluationratingses" );
	deleteAllTableData( "quarters" );
	deleteAllTableData( "skillratingses" );
	insertStudents();
	insertFaculty();
	insertRoles();
	insertSubjects();
	insertPreviousTranscripts();
	insertGraduateTracking();
	insertWeekly();
	insertArtifacts();
	insertBodyOfWork();
	insertDaily();
	insertMonthlyEvaluationRatings();
	insertMonthlySummaryRatings();
	insertEvaluationRatings();
	insertQuarter();
	insertSkillRatings();

	return $rc;
}
###############################################################################
#   cleanUp
#
#   DESCRIPTION:
#      Cleans up this program.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub cleanUp()
{
	$CONFIG{date} = "$0 complete: " . perllib::Funcs::getDate();
	logIt($CONFIG{date} . "\n\n\n");
	$CONFIG{funcs}->closeMe() if( defined( $CONFIG{funcs} ) );
}
###############################################################################
#   resetInit()
#
#   DESCRIPTION:
#       Resets the initialization after we get the command line args.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub resetInit(\%)
{
	my( $myRef )		= @_;
	$CONFIG{host}		= $myRef->{'host'} if( defined( $myRef->{'host'} ) );
	$CONFIG{port}		= $myRef->{'port'} if( defined( $myRef->{'port'} ) );
	$CONFIG{app}		= $myRef->{'app'} if( defined( $myRef->{'app'} ) );
	$CONFIG{log_file}	= $myRef->{'log_file'} if( defined( $myRef->{'log_file'} ) );
	$CONFIG{debug}		= $myRef->{'debug'} if( defined( $myRef->{'debug'} ) );
	$CONFIG{stdout}		= $myRef->{'stdout'} if( defined( $myRef->{'stdout'} ) );
	$CONFIG{config}		= $myRef->{'config'} if( defined( $myRef->{'config'} ) );
	$CONFIG{log_file}	=~ s/\.pl//;
	my $funcsObject	= new perllib::Funcs(
							'LOGFILE'		=> $CONFIG{log_file},
							'STDOUTFLAG'	=> $CONFIG{stdout},
							'DEBUG'			=> $CONFIG{debug} 
	);
	$CONFIG{funcs}			= $funcsObject;
	if( 0 )
	{
	$CONFIG{configObject}	= new perllib::MyConfig( file => $CONFIG{config}, funcs_object => $CONFIG{funcs} );
	if( defined( $CONFIG{configObject} ) )
	{
		if( defined( $myRef->{'batch_id'} ) )
		{
			$CONFIG{batch_id}		= $myRef->{'batch_id'};
		}
		else
		{
			$CONFIG{batch_id}		= $CONFIG{configObject}->getValue( "BATCH_CD" );
		}
		if( !defined( $CONFIG{batch_id} ) )
		{
			logIt( "main::resetInit(): batch_id is not defined.\n" );
			cleanUp();
			exit( 1 );
		}
		if( defined( $myRef->{'search_columns'} ) )
		{
			$CONFIG{search_columns}	= $myRef->{'search_columns'};
		}
		else
		{
			my $search_columns		= $CONFIG{configObject}->getValue( "SEARCH_COLUMNS" );
			if( defined( $search_columns ) && $search_columns ne "" )
			{
				$search_columns			=~ s/\,/=/g;
				$CONFIG{search_columns}	= $search_columns;
			}
			else
			{
				$CONFIG{search_columns}	= "";
			}
		}
		if( defined( $myRef->{'ccb_return_columns'} ) )
		{
			$CONFIG{ccb_return_columns}	= $myRef->{'ccb_return_columns'};
		}
		else
		{
			my $return_columns		= $CONFIG{configObject}->getValue( "CCB_RETURN_COLUMNS" );
			$return_columns			=~ s/\,/=/g;
			$CONFIG{ccb_return_columns}	= $return_columns;
		}
		if( !defined( $CONFIG{ccb_return_columns} ) )
		{
			logIt( "main::resetInit(): ccb_return_columns is not defined.\n" );
			cleanUp();
			exit( 1 );
		}
		if( defined( $myRef->{'oubi_return_columns'} ) )
		{
			$CONFIG{oubi_return_columns}	= $myRef->{'oubi_return_columns'};
		}
		else
		{
			my $return_columns		= $CONFIG{configObject}->getValue( "OUBI_RETURN_COLUMNS" );
			$return_columns			=~ s/\,/=/g;
			$CONFIG{oubi_return_columns}	= $return_columns;
		}
		if( !defined( $CONFIG{oubi_return_columns} ) )
		{
			logIt( "main::resetInit(): oubi_return_columns is not defined.\n" );
			cleanUp();
			exit( 1 );
		}
		if( !defined( $CONFIG{oubi_user} ) )
		{
			logIt( "main::resetInit(): oubi_user is not defined.\n" );
			cleanUp();
			exit( 1 );
		}
		if( !defined( $CONFIG{oubi_pwd} ) )
		{
			logIt( "main::resetInit(): oubi_pwd is not defined.\n" );
			cleanUp();
			exit( 1 );
		}
		if( defined( $myRef->{'ccb_view'} ) )
		{
			$CONFIG{ccb_view}		= $myRef->{'ccb_view'};
		}
		else
		{
			$CONFIG{ccb_view}		= $CONFIG{configObject}->getValue( "CCB_VIEW" );
		}
		if( !defined( $CONFIG{ccb_view} ) )
		{
			logIt( "main::resetInit(): ccb_view is not defined.\n" );
			cleanUp();
			exit( 1 );
		}
		if( defined( $myRef->{'ccb_table'} ) )
		{
			$CONFIG{ccb_table}		= $myRef->{'ccb_table'};
		}
		else
		{
			$CONFIG{ccb_table}		= $CONFIG{configObject}->getValue( "CCB_TABLE" );
		}
		if( !defined( $CONFIG{ccb_table} ) )
		{
			logIt( "main::resetInit(): ccb_table is not defined.\n" );
			cleanUp();
			exit( 1 );
		}
		if( defined( $myRef->{'ccb_pk_column'} ) )
		{
			$CONFIG{ccb_pk_column}		= $myRef->{'ccb_pk_column'};
		}
		else
		{
			$CONFIG{ccb_pk_column}		= $CONFIG{configObject}->getValue( "CCB_PK_COLUMN" );
		}
		if( !defined( $CONFIG{ccb_pk_column} ) )
		{
			$CONFIG{ccb_pk_column} = "";
		#	logIt( "main::resetInit(): ccb_pk_column is not defined.\n" );
		#	cleanUp();
		#	exit( 1 );
		}
		if( defined( $myRef->{'ccb_start_column'} ) )
		{
			$CONFIG{ccb_start_column}		= $myRef->{'ccb_start_column'};
		}
		else
		{
			$CONFIG{ccb_start_column}		= $CONFIG{configObject}->getValue( "CCB_START_COLUMN" );
		}
		if( !defined( $CONFIG{ccb_start_column} ) )
		{
			$CONFIG{ccb_start_column} = "";
		#	logIt( "main::resetInit(): ccb_start_column is not defined.\n" );
		#	cleanUp();
		#	exit( 1 );
		}
		if( defined( $myRef->{'ccb_end_column'} ) )
		{
			$CONFIG{ccb_end_column}		= $myRef->{'ccb_end_column'};
		}
		else
		{
			$CONFIG{ccb_end_column}		= $CONFIG{configObject}->getValue( "CCB_END_COLUMN" );
		}
		if( !defined( $CONFIG{ccb_end_column} ) )
		{
			$CONFIG{ccb_end_column} = "";
		#	logIt( "main::resetInit(): ccb_end_column is not defined.\n" );
		#	cleanUp();
		#	exit( 1 );
		}
		if( defined( $myRef->{'oubi_view'} ) )
		{
			$CONFIG{oubi_view}		= $myRef->{'oubi_view'};
		}
		else
		{
			$CONFIG{oubi_view}		= $CONFIG{configObject}->getValue( "OUBI_VIEW" );
		}
		if( !defined( $CONFIG{oubi_view} ) )
		{
			logIt( "main::resetInit(): oubi_view is not defined.\n" );
			cleanUp();
			exit( 1 );
		}
		if( defined( $myRef->{'oubi_table'} ) )
		{
			$CONFIG{oubi_table}		= $myRef->{'oubi_table'};
		}
		else
		{
			$CONFIG{oubi_table}		= $CONFIG{configObject}->getValue( "OUBI_TABLE" );
		}
		if( !defined( $CONFIG{oubi_table} ) )
		{
			logIt( "main::resetInit(): oubi_table is not defined.\n" );
			cleanUp();
			exit( 1 );
		}
		if( defined( $myRef->{'oubi_pk_column'} ) )
		{
			$CONFIG{oubi_pk_column}		= $myRef->{'oubi_pk_column'};
		}
		else
		{
			$CONFIG{oubi_pk_column}		= $CONFIG{configObject}->getValue( "OUBI_PK_COLUMN" );
		}
		if( !defined( $CONFIG{oubi_pk_column} ) )
		{
			$CONFIG{oubi_pk_column} = "";
		#	logIt( "main::resetInit(): oubi_pk_column is not defined.\n" );
		#	cleanUp();
		#	exit( 1 );
		}
		if( defined( $myRef->{'oubi_start_column'} ) )
		{
			$CONFIG{oubi_start_column}		= $myRef->{'oubi_start_column'};
		}
		else
		{
			$CONFIG{oubi_start_column}		= $CONFIG{configObject}->getValue( "OUBI_START_COLUMN" );
		}
		if( !defined( $CONFIG{oubi_start_column} ) )
		{
			$CONFIG{oubi_start_column} = "";
		#	logIt( "main::resetInit(): oubi_start_column is not defined.\n" );
		#	cleanUp();
		#	exit( 1 );
		}
		if( defined( $myRef->{'oubi_end_column'} ) )
		{
			$CONFIG{oubi_end_column}		= $myRef->{'oubi_end_column'};
		}
		else
		{
			$CONFIG{oubi_end_column}		= $CONFIG{configObject}->getValue( "OUBI_END_COLUMN" );
		}
		if( !defined( $CONFIG{oubi_end_column} ) )
		{
			$CONFIG{oubi_end_column} = "";
			#logIt( "main::resetInit(): oubi_end_column is not defined.\n" );
			#cleanUp();
			#exit( 1 );
		}
		if( defined( $myRef->{'diff_view'} ) )
		{
			$CONFIG{diff_view}		= $myRef->{'diff_view'};
		}
		else
		{
			$CONFIG{diff_view}		= $CONFIG{configObject}->getValue( "DIFF_VIEW" );
		}
		if( !defined( $CONFIG{diff_view} ) )
		{
			logIt( "main::resetInit(): diff_view is not defined.\n" );
			cleanUp();
			exit( 1 );
		}
		if( defined( $myRef->{'diff_table'} ) )
		{
			$CONFIG{diff_table}		= $myRef->{'diff_table'};
		}
		else
		{
			$CONFIG{diff_table}		= $CONFIG{configObject}->getValue( "DIFF_TABLE" );
		}
		if( !defined( $CONFIG{diff_table} ) )
		{
			logIt( "main::resetInit(): diff_table is not defined.\n" );
			cleanUp();
			exit( 1 );
		}
		if( $CONFIG{file} !~ m/$CONFIG{batch_id}/ )
		{
			my $file = $CONFIG{file};
			$file =~ s/\.xlsx$/_$CONFIG{batch_id}\.xlsx/;
			$CONFIG{file} = $file;
		}
	}
	else
	{
		logIt( "main::resetInit(): Unable to retrieve the ETL configuration object for " . $CONFIG{config} . "\n" );
		cleanUp();
		exit( 1 );
	}
	}
}
###############################################################################
#   getCmdOptions
#
#   DESCRIPTION:
#       Gets the command line options for this program.
#
#   RETURN(S):
#       Nothing.
###############################################################################
sub getCmdOptions()
{
	my $href_opts = {};
	my $rc = undef;
	my $program = $0;
	my $usage_string = 
		$program . "\n"  .
		"\t[-host]            -- host name for webhost(optional: default is $CONFIG{host}).\n" .
		"\t[-port]            -- port number for webhost(optional: default is $CONFIG{port}).\n" .
		"\t[-app]             -- application name(optional: default is $CONFIG{app}).\n" .
		"\t[-log_file]        -- full path name of log (optional: default is $CONFIG{log_file}).\n" .
		"\t[-debug]           -- set debug option on.\n" . 
		"\t[-stdout]          -- set standard output flag option on.\n" . 
		"\t[-help]            -- displays the usage.\n" .
		"Example: populate.pl -debug -stdout\n"; 


	$rc = GetOptions(
				'host=s'			=> \$href_opts->{'host'},
				'port=s'			=> \$href_opts->{'port'},
				'app=s'				=> \$href_opts->{'app'},
				'log_file=s'		=> \$href_opts->{'log_file'},
				'debug'				=> \$href_opts->{'debug'},
				'stdout'			=> \$href_opts->{'stdout'},
				'help'				=> \$href_opts->{'help'}
			 );

	my @missing_opts;
	my %opt_display = (
			'host'			=> 'host',
			'port'			=> 'port',
			'app'			=> 'app',
			'log_file'		=> 'log_file',
			'debug'			=> 'debug',
			'stdout'		=> 'stdout',
			'help'			=> 'help'
		);

	if( ( $rc ne 1 ) or ( $href_opts->{'help'} ) )
	{
		print $usage_string, "\n";
		exit(1);
	}
	# Check for required options and notify the user which required options are missing
	#foreach my $option (qw(connect config))
	#{
	#	push (@missing_opts, $option) unless $href_opts->{$option};
	#}

	(scalar(@missing_opts) > 0) && 
	do 
	{
		my $msg;
		$msg = "$0 Must provide the following command-line options:\n";
		print "Must provide the following command-line options: ";
		foreach my $opt (@missing_opts)
		{
			print "-", $opt_display{$opt}, ' ';
			$msg .= "\t-" . $opt_display{$opt} . "\n";
		}
		print "\n";
		print $usage_string, "\n";
		#die;
		fatalError( $msg );
	};

	resetInit( %$href_opts );
}
################################################################################
#
#   fatalError()
#
#   DESCRIPTION:
#       Initializes things for this application.
#
#   RETURN(S):
#       Nothing.
################################################################################
sub fatalError(;$)
{        
	my ($msg) = @_;

	logIt($msg); 
	#croak();
	cleanUp();
	exit( 1 );
}

################################################################################
#
#   initialize()
#
#   DESCRIPTION:
#       Initializes things for this application.
#
#   RETURN(S):
#       Nothing.
################################################################################
sub initialize()
{
	$|++;    ## forces an fflush(3) after every write or print

	$CONFIG{date}			= perllib::Funcs::getDate();
	$CONFIG{file_date}		= perllib::Funcs::getFileTimeStamp();
	$CONFIG{ret}			= 0;
	$CONFIG{debug}			= 0;
	$CONFIG{stdout}		    = 0;
	$CONFIG{port}			= "8088";
	$CONFIG{host}			= "localhost";
	$CONFIG{app}			= "MySchool";
	#$CONFIG{config}			= "./VC_SA.cfg";
	my $osName = lc( $^O );
	if( $osName =~ m/mswin/ )
	{
		$CONFIG{log_file}		= "c:/temp/" . basename($0);
	}
	elsif( $osName =~ m/cygwin/ )
	{
		$CONFIG{log_file}		= "/cygdrive/c/temp/" . basename($0);
	}
	else
	{
		$CONFIG{log_file}		= "/tmp/" . basename($0);
	}
	$CONFIG{log_file}		=~ s/\.pl//;
}

################################################################################
#
#   printVersionInfo()
#
#   DESCRIPTION:
#       Logs the version information about this script.
#
#   RETURN(S):
#       Nothing.
################################################################################
sub printVersionInfo()
{
	my $modDate  = (stat( $0 ))[9]; 
	my $currDate = perllib::Funcs::getDate( $modDate );

	logIt( "Perl Script: $0\n" );
	logIt( "Version Date: $currDate\n" );
}
################################################################################
#
#   printInfo()
#
#   DESCRIPTION:
#       Logs the information about this program.
#
#   RETURN(S):
#       Nothing.
################################################################################
sub printInfo()
{
	############################################################################
	#   Log myself.
	############################################################################
	my $host			= hostname;

	logIt( "Todays date = $CONFIG{date}\n" ); 
	logIt( "$0 Program Started\n" );
	logIt( "                  Log file is: $CONFIG{log_file}\n" );
	logIt( "               Stdout flag is: on\n" ) if( $CONFIG{stdout} );
	logIt( "               Stdout flag is: off\n" ) if( !$CONFIG{stdout} );
	logIt( "                Debug flag is: on\n" ) if( $CONFIG{debug} );
	logIt( "                Debug flag is: off\n" ) if( !$CONFIG{debug} );
	logIt( "\n" );
	printCONFIG() if( $CONFIG{debug} >= 1);
}
################################################################################
#
#   printCONFIG()
#
#   DESCRIPTION:
#       Logs the CONFIG hash for this program.
#
#   RETURN(S):
#       Nothing.
################################################################################
sub printCONFIG()
{
	my( $value );
	my( $key );
	logIt( "\nBegin contents of the CONFIG hash.\n" );
	#logIt( Dumper( \%CONFIG ) );
	if( 1 )
	{
	foreach $key (sort keys %CONFIG) 
	{
		logIt( "$key = $CONFIG{$key}\n" ) if( $key ne "ccb_pwd"  && $key ne "oubi_pwd" );
		#logIt( "$key = $CONFIG{$key}\n" );
	}
	}
	logIt( "\nEnd contents of the CONFIG hash.\n" );
}
################################################################################
#
#   logIt()
#
#   DESCRIPTION:
#       Logs the the message.
#
#   RETURN(S):
#       Nothing.
################################################################################
sub logIt(;$)
{
	my( $msg ) = @_;

	if( defined( $CONFIG{funcs} ) )
	{
		$CONFIG{funcs}->logIt( $msg );
	}
	else
	{
		print $msg;
	}
}
################################################################################
#
#   debug()
#
#   DESCRIPTION:
#       Logs the the message.
#
#   RETURN(S):
#       Nothing.
################################################################################
sub debug(;$)
{
	my( $msg ) = @_;

	return if( $CONFIG{debug} == 0 );
	logIt( $msg );
}
###################################
#   End subroutine section.
###################################
