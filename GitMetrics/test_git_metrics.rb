# <package>
#     Git Metrics
# <.package>
# <description>
#     Tests the git metric functions
# <.description>
# <keywords>
#     unit test
# <.keywords>

require_relative 'git_metrics'
require 'test/unit'

class TestGitMetrics < Test::Unit::TestCase

  def test_commit_example
    assert_equal 2, num_commits(["commit abc", "commit 123"]), "Should have counted two commits"
  end

  def test_dates_with_three_days
    exp = [ "Date:  Sun Jan 26 21:25:22 2014 -0600",
            "Date:  Sun Jan 23 21:25:22 2014 -0600",
            "Date:  Sun Jan 25 21:25:22 2014 -0600"]
    assert_equal 3, days_of_development(exp), "Should have been a 3 days difference"

    # Another simple test
    exp.push "Date: Sun Jan 10 21:24:22 2014 -0600"
    assert_equal 16, days_of_development(exp), "Should have been 16 days"

    # Test year/month bridge
    exp.push "Date: Sun Dec 5 21:24:22 2013 -0600"
    assert_equal 52, days_of_development(exp), "Should be 52"
  end

  #Add more tests here
  def test_2_authors
    authors = ["<thehulk@angry.rawr>", "<thor@hammertime.asgard>"]
    assert_equal 2, num_developers(authors)
  end
end
